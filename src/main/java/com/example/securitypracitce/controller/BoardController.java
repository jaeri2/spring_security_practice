package com.example.securitypracitce.controller;

import com.example.securitypracitce.domain.dto.BoardReq;
import com.example.securitypracitce.domain.dto.BoardRes;
import com.example.securitypracitce.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 글쓰기 -> USER, ADMIN 모두 허용
 * 글수정 -> 본인이 쓴 글만 수정 가능
 * 글삭제 -> 본인이 쓴 글 또는 ADMIN 허용
 */
@Api(tags = {"게시글"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 글 작성
    @PostMapping("/boards")
    @ApiOperation(value = "게시글 작성 API", notes = "글을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardReq", value = "boardReq dto", required = true, dataType = "BoardReq")
    })
    public ResponseEntity<String> createBoards(@RequestBody BoardReq boardReq) {
        boardService.createBoard(boardReq);
        return ResponseEntity.ok("글 작성이 완료되었습니다.");
    }

    // 글 조회
    @GetMapping("/boards/{id}")
    @ApiOperation(value = "게시글 조회 API", notes = "게시글을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1", required = true)
    })
    public ResponseEntity<BoardRes> readBoards(@PathVariable Long id) {
        if(!boardService.existsById(id)){
            throw new IllegalArgumentException("존재하지않는 게시글입니다.");
        }

        return ResponseEntity.ok(boardService.readBoards(id));
    }

    // 글 수정
    @PatchMapping("/boards/{id}")
    @ApiOperation(value = "게시글 수정 API", notes = "게시글을 수정합니다.(본인이 작성한 게시글만 가능)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1", required = true),
            @ApiImplicitParam(name = "boardReq", value = "boardReq dto", required = true, dataType = "BoardReq")
    })
    public ResponseEntity<BoardRes> updateBoards(@PathVariable Long id, @RequestBody BoardReq boardReq) {
        if(!boardService.existsById(id)){
            throw new IllegalArgumentException("존재하지않는 게시글입니다.");
        }

        return ResponseEntity.ok(boardService.updateBoards(id, boardReq));
    }


    @DeleteMapping("/boards/{id}")
    @ApiOperation(value = "게시글 삭제 API", notes = "게시글을 삭제합니다.(본인이 작성한 게시글 or ADMIN)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1", required = true)
    })
    public ResponseEntity<String> deleteBoards(@PathVariable Long id) {
        if(!boardService.existsById(id)){
            throw new IllegalArgumentException("존재하지않는 게시글입니다.");
        }
        boardService.deleteBoards(id);
        return ResponseEntity.ok("글이 삭제되었습니다.");
    }

}
