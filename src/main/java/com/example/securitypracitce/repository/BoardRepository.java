package com.example.securitypracitce.repository;

import com.example.securitypracitce.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
