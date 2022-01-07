package com.example.securitypracitce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRes {
    private Long boardId;
    private String title;
    private String contents;
    private String writer;
    private String writeDate;
}
