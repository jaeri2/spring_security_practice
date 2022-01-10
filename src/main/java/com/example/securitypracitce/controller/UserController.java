package com.example.securitypracitce.controller;

import com.example.securitypracitce.domain.dto.LoginDto;
import com.example.securitypracitce.domain.dto.TokenDto;
import com.example.securitypracitce.domain.dto.UserDto;
import com.example.securitypracitce.security.JwtFilter;
import com.example.securitypracitce.security.TokenProvider;
import com.example.securitypracitce.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = {"사용자"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 1. 회원가입
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입 API", notes = "사용자 정보를 저장합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto", value = "사용자 dto", required = true, dataType = "UserDto")
    })
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // 2. 로그인
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        log.info(">>> -------------- 로그인 ----------------");
        // UsernamePasswordAuthenticationToken -> Authentication 인터페이스의 구현체, 사용자에게 리턴되는 Jwt 토큰이 아니라 Spring이 인증로직에서 사용하는 토큰이다.
        // Authentication을 구현한 구현체만이 AuthenticationManager를 통한 인증과정을 수행할 수 있다.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        log.info(">>> authenticationToken.toString() : " + authenticationToken.toString());

        // 인증정보 생성 및 SecurityContext에 저장
        Authentication authentication =
                authenticationManagerBuilder.getObject() // authenticationManagerBuilder.getObject() -> AuthenticationManager 리턴된다.
                .authenticate(authenticationToken); // AuthenticationManager.authenticate ->
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info(">>> authentication : " + authentication.toString());

        // jwt token 생성
        String jwtToken = tokenProvider.createToken(authentication);
        log.info(">>> jwtToken : " + jwtToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

        return new ResponseEntity<>(new TokenDto(jwtToken), httpHeaders, HttpStatus.OK);
    }

    // 3. 회원조회(only ADMIN)
    @GetMapping("/api/members")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserDto>> findAllMember() {
        return ResponseEntity.ok(userService.findAllMember());
    }
}
