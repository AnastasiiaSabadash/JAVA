package com.fashion.repository;

import com.fashion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Метод для пошуку користувача за іменем (потрібен для Security)
    Optional<User> findByUsername(String username);
}