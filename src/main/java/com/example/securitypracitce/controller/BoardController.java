package com.example.securitypracitce.controller;

import com.example.securitypracitce.domain.dto.BoardReq;
import com.example.securitypracitce.domain.dto.BoardRes;
import com.example.securitypracitce.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 글쓰기 -> USER, ADMIN 모두 허용
 * 글수정 -> 본인이 쓴 글만 수정 가능
 * 글삭제 -> 본인이 쓴 글 또는 ADMIN 허용
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 글 작성
    @PostMapping("/boards")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> createBoards(@RequestBody BoardReq boardReq) {
        boardService.createBoard(boardReq);
        return ResponseEntity.ok("글 작성이 완료되었습니다.");
    }

    // 글 조회
    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardRes> readBoards(@PathVariable Long id) {
        if(!boardService.existsById(id)){
            throw new IllegalArgumentException("존재하지않는 게시글입니다.");
        }

        return ResponseEntity.ok(boardService.readBoards(id));
    }

    // 글 수정
    @PatchMapping("/boards/{id}")
    public ResponseEntity<BoardRes> updateBoards(@PathVariable Long id, @RequestBody BoardReq boardReq) {
        if(!boardService.existsById(id)){
            throw new IllegalArgumentException("존재하지않는 게시글입니다.");
        }

        return ResponseEntity.ok(boardService.updateBoards(id, boardReq));
    }
}
