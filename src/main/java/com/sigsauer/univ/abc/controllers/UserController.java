package com.sigsauer.univ.abc.controllers;

import com.sigsauer.univ.abc.models.exceptions.EmailAlreadyExistException;
import com.sigsauer.univ.abc.models.exceptions.UserNotFoundException;
import com.sigsauer.univ.abc.models.exceptions.UsernameAlreadyExistException;
import com.sigsauer.univ.abc.models.users.User;
import com.sigsauer.univ.abc.payload.request.LoginRequest;
import com.sigsauer.univ.abc.payload.request.UserRequest;
import com.sigsauer.univ.abc.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8081", "http://192.168.1.112:8081/" },
        methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        allowedHeaders = "*",
        maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(@RequestParam(required = false) String username) {
        log.info("IN UserController GET request with param: {}",username);
        List<User> users = userService.getAll();
        return ResponseEntity.ok( username == null ? users :
                users.stream().filter(u -> u.getUsername().contains(username)).collect(Collectors.toList()));
    }



    @GetMapping("{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        log.info("IN UserController GET(id) request");
        try {
            User user = userService.getOne(id);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserRequest user) {
        log.info("IN UserController POST request");
        try {
            User result = userService.add(user.toUser());
            result = userService.updateRoles(result.getId(),user.getArrayRoles());
            return ResponseEntity.ok(result);
        } catch (UsernameAlreadyExistException | EmailAlreadyExistException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserRequest user) {
        log.info("IN UserController PUT request");
        try {
            User result = userService.update(id,user.toUser());
            result = user.hasRoles() ? userService.updateRoles(result.getId(),user.getArrayRoles()) : result;
            return ResponseEntity.ok(result);
        }catch (UsernameAlreadyExistException | EmailAlreadyExistException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("{id}")
    public ResponseEntity updatePassword(@PathVariable Long id, @RequestBody LoginRequest loginRequest) {
        log.info("IN UserController PATCH request");
        try {
            User user = userService.updatePassword(id, loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            Map<String, String> message = new HashMap<>();
            message.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }

    }
}
