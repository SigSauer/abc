package com.sigsauer.univ.abc.security.services;

import com.sigsauer.univ.abc.models.users.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getOne(Long id);

    User add(User user);

    User update(Long id, User user);

    User updatePassword(Long id, String password);

    User updateRoles(Long id, String... roles);
}
