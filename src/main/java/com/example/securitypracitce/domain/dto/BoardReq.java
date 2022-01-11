package com.example.securitypracitce.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardReq {

    @ApiModelProperty(value = "제목", example = "게시글 제목입니다", required = true)
    private String title;

    @ApiModelProperty(value = "내용", example = "게시글 내용입니다", required = true)
    private String contents;

}
