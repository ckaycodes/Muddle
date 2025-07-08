package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// --- talks to the database (like saving an order) ---

public interface UserRepository extends JpaRepository<User, Long> {
    // add custom queries here later if needed
}
