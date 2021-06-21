package com.example.demo.components.user;

import com.example.demo.components.user.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public @ResponseBody
    ResponseEntity<Object> index() {
        Collection<UserDTO> userDTOs = UserMapper.INSTANCE.userEntitiesToUserDTOs(userRepository.findAll());
        return ResponseEntity.ok().body(userDTOs);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User>
    show(@PathVariable int id) {
        User user;
        user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<User> store(@RequestBody UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<User> update(@PathVariable int id, @RequestBody UserDTO userDTO) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setName(userDTO.getName());
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }
}
