package com.sigsauer.univ.abc.repository;

import com.sigsauer.univ.abc.models.users.ERole;
import com.sigsauer.univ.abc.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
