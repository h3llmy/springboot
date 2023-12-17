package com.testing.domain.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.testing.domain.users.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public Page<User> findAll(Pageable pageable);

    public User findByEmail(String email);

    public User findByUsername(String username);
}
