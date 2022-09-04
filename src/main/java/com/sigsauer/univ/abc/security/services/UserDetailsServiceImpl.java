package com.sigsauer.univ.abc.security.services;

import com.sigsauer.univ.abc.models.exceptions.EmailAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.UserNotFoundException;
import com.sigsauer.univ.abc.models.exceptions.UsernameAlreadyExistException;
import com.sigsauer.univ.abc.models.users.ERole;
import com.sigsauer.univ.abc.models.users.Role;
import com.sigsauer.univ.abc.models.users.User;
import com.sigsauer.univ.abc.repository.RoleRepository;
import com.sigsauer.univ.abc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;



	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}


	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User getOne(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
	}


	@Override
	public User add(User user) {
		if(userRepository.existsByUsername(user.getUsername()))
			throw new UsernameAlreadyExistException(user.getUsername());
		else if(userRepository.existsByEmail(user.getEmail()))
			throw new EmailAlreadyExistException(user.getEmail());
		else {
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
			user.setPassword(encoder.encode(user.getUsername()));
//			user.setRoles(roles);
			user = userRepository.save(user);
			return user;
		}
	}

	@Override
	public User update(Long id, User user) throws UsernameAlreadyExistException, EmailAlreadyExistException {
		User old = getOne(id);
		if(!user.getUsername().equals(old.getUsername()) && userRepository.existsByUsername(user.getUsername()))
				throw new UsernameAlreadyExistException(user.getUsername());
		else if(!user.getEmail().equals(old.getEmail()) &&  userRepository.existsByEmail(user.getEmail()))
			throw new EmailAlreadyExistException(user.getEmail());
		else {
			user.setId(old.getId());
			user.setPassword(old.getPassword());
			user.setRoles(old.getRoles());
			return userRepository.save(user);

		}
	}

	@Override
	public User updatePassword(Long id, String password) {
		User user = getOne(id);
		user.setPassword(encoder.encode(password));
		return userRepository.save(user);
	}

	@Override
	public User updateRoles(Long id, String... roles) {
		Set<Role> roleSet = new HashSet<>();
		User user = getOne(id);
		Arrays.stream(roles).forEach(r -> {
			Role userRole = roleRepository.findByName(ERole.valueOf(r))
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roleSet.add(userRole);
		});
		user.setRoles(roleSet);
		return userRepository.save(user);
	}

}
