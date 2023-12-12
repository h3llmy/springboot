package com.testing.domain.users;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.testing.domain.users.dto.CreateUserDto;
import com.testing.domain.users.dto.UpdateUserDto;
import com.testing.domain.users.entity.User;
import com.testing.util.Controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/user")
public class UserController extends Controller {
    @Autowired
    private UserService userService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Valid CreateUserDto createUserDto) {
        if (!Objects.equals(createUserDto.getPassword(), createUserDto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password do not match");
        }
        User userFindByEmail = userService.getUserByEmail(createUserDto.getEmail());
        if (userFindByEmail != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists");
        }
        User userFindByUsername = userService.getUserByUsername(createUserDto.getUsername());
        if (userFindByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
        }
        return userService.saveUser(createUserDto);
    }
    
    @GetMapping
    public Map<String, Object> getAllUser(@RequestParam(required = false, defaultValue = "1") int page) {
        return userService.getAllUsers(page);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserDto newUser) {
        return userService.updateUser(id, newUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
