package com.ntg.JobHunter.controller;

import com.ntg.JobHunter.domain.User;
import com.ntg.JobHunter.util.error.IdInvalidException;
import com.ntg.JobHunter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/v1/auth/account")
    @CrossOrigin
    public ResponseEntity<String> helloToClient() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello Client");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws IdInvalidException {
        if (id > 100) {
            throw new IdInvalidException("id phải bé hơn 100");
        }
        User user = userService.handleGetUser(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("bad gate way");
    }
    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        userService.handleSaveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/user")
    public String putUser() {
        return "hehe";
    }


    @DeleteMapping("/user/{id}")
    public String deleteUSer(@PathVariable Long id) throws IdInvalidException{
        if (id >= 100) {
            throw new IdInvalidException("Id loi roi");
        }
        return "delete";
    }
}
