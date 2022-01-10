package com.example.securitypracitce.service;

import com.example.securitypracitce.domain.dto.BoardReq;
import com.example.securitypracitce.domain.dto.BoardRes;
import com.example.securitypracitce.domain.entity.Board;
import com.example.securitypracitce.domain.entity.User;
import com.example.securitypracitce.repository.BoardRepository;
import com.example.securitypracitce.repository.UserRepository;
import com.example.securitypracitce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    public boolean existsById(Long id) {
        return boardRepository.existsById(id);
    }


    // 글 작성
    public void createBoard(BoardReq boardReq) {
        String username = SecurityUtil.getCurrentUsername().orElseThrow(() -> new IllegalArgumentException("Security Context에 인증 정보가 없습니다."));
        User findUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 사용자 정보입니다."));

        Board newBoard = Board.builder()
                .title(boardReq.getTitle())
                .contents(boardReq.getContents())
                .writer(findUser.getNickname())
                .user(findUser)
                .build();

        boardRepository.save(newBoard);
    }

    // 글 조회
    public BoardRes readBoards(Long id) {
        Board findBoard = boardRepository.findById(id).get();

        return BoardRes.builder()
                .boardId(id)
                .title(findBoard.getTitle())
                .contents(findBoard.getContents())
                .writer(findBoard.getWriter())
                .writeDate(findBoard.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }

    // 글 수정
    @Transactional
    public BoardRes updateBoards(Long id, BoardReq boardReq) {
        Board findBoard = boardRepository.findById(id).get();

        String username = SecurityUtil.getCurrentUsername().orElseThrow(() -> new IllegalArgumentException("Security Context에 인증 정보가 없습니다."));
        if (!findBoard.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("해당 글을 수정할 수 있는 권한이 없습니다.");
        }

        findBoard.changeBoard(boardReq.getTitle(), boardReq.getContents());

        return BoardRes.builder()
                .boardId(id)
                .title(findBoard.getTitle())
                .contents(findBoard.getContents())
                .writer(findBoard.getWriter())
                .writeDate(findBoard.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }

    // 글 삭제
    @Transactional
    public void deleteBoards(Long id) {
        boolean isAdmin = SecurityUtil.hasAdminRole();
        String username = SecurityUtil.getCurrentUsername().orElseThrow(() -> new IllegalArgumentException("Security Context에 인증 정보가 없습니다."));

        log.info("-->> deleteBoard - isAdmin : " + isAdmin);
        log.info("-->> deleteBoard - username : " + username);

        Board findBoard = boardRepository.findById(id).get();

        if (!isAdmin && !findBoard.getUser().getUsername().equals(username)) {
            log.info("-->> deleteBoard 권한없음 실행불가");
            throw new IllegalArgumentException("해당 글을 삭제할 수 있는 권한이 없습니다.");
        }

        boardRepository.deleteById(id);

    }


}
