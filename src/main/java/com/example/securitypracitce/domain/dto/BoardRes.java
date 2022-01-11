package com.example.securitypracitce.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRes {

    @ApiModelProperty(value = "게시글 id", example = "1", required = true)
    private Long boardId;

    @ApiModelProperty(value = "제목", example = "게시글 제목입니다", required = true)
    private String title;

    @ApiModelProperty(value = "내용", example = "게시글 내용입니다", required = true)
    private String contents;

    @ApiModelProperty(value = "작성자 닉네임", example = "홍길동", required = true)
    private String writer;

    @ApiModelProperty(value = "게시일자", example = "2022-01-11", required = true)
    private String writeDate;
}
