package com.testing.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.testing.domain.users.dto.CreateUserDto;
import com.testing.domain.users.dto.UpdateUserDto;
import com.testing.domain.users.entity.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getAllUsers(int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();
        Long totalUser = userPage.getTotalElements();
        int totalPage = userPage.getTotalPages();
        Map<String, Object> response = new HashMap<>();

        response.put("totalUser", totalUser);
        response.put("totalPage", totalPage);
        response.put("currentPage", page);
        response.put("data", users);
        return response;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + " not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(CreateUserDto userDto) {
        checkIfEmailOrUsernameExists(userDto.getEmail(), userDto.getUsername());

        User user = User.build(0L, userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        return userRepository.save(user);
    }

    public User updateUser(Long id, UpdateUserDto userDto) {
        checkIfEmailOrUsernameExists(userDto.getEmail(), userDto.getUsername());

        User user = this.getUserById(id);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    private void checkIfEmailOrUsernameExists(String email, String username) {
        User userFindByEmail = this.getUserByEmail(email);
        if (userFindByEmail != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User userFindByUsername = this.getUserByUsername(username);
        if (userFindByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
    }

    public Map<String, String> deleteUser(Long id) {
        this.getUserById(id);
        userRepository.deleteById(id);
        return Collections.singletonMap("message", "user " + id + " has been deleted");
    }
}
