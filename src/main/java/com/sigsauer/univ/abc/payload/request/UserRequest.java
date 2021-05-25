package com.sigsauer.univ.abc.payload.request;

import com.sigsauer.univ.abc.models.users.User;

import java.util.List;

public class UserRequest {

    private String username;
    private String name;
    private String email;
    private List<String> roles;

    public UserRequest() {
    }

    public User toUser() {
        return new User(this.username, this.name, this.email, this.username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getArrayRoles() {
        return roles.toArray(new String[0]);

    }
    public List<String> getRoles() {
        return roles;
    }

    public boolean hasRoles() {
        return !this.roles.isEmpty();
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
