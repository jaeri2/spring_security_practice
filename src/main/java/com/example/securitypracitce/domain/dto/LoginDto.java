package com.example.securitypracitce.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @ApiModelProperty(value = "이메일", example = "test@test.com", required = true)
    private String username;

    @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
    private String password;
}
