package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // add custom queries here later if needed
}
