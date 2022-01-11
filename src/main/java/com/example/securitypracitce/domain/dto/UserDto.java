package com.example.securitypracitce.domain.dto;

import com.example.securitypracitce.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @ApiModelProperty(value = "이메일", example = "test@test.com", required = true)
    private String username;

    @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
    private String password;

    @ApiModelProperty(value = "닉네임", example = "홍길동", required = true)
    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName().name()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
