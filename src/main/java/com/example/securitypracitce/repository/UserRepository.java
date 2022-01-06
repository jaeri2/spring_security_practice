package com.example.securitypracitce.repository;

import com.example.securitypracitce.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
