package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


}