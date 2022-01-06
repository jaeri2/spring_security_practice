package com.example.securitypracitce.repository;

import com.example.securitypracitce.domain.entity.Authority;
import com.example.securitypracitce.domain.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, RoleType> {
}
