package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.payload.request.UserUpdateRequest;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Object> index() {
        return ResponseEntity
                .ok()
                .body(StreamSupport.stream(userRepository.findAll().spliterator(), false)
                        .map(UserResponse::new)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse>
    show(@PathVariable UUID id) {
        User user;
        user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(new UserResponse(user));
    }


    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public @ResponseBody
    ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setUsername(userUpdateRequest.getUsername());
        user.setEmail(userUpdateRequest.getEmail());
        userRepository.save(user);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
