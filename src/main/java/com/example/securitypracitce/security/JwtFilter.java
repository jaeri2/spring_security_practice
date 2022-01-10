package com.example.securitypracitce.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(">>> doFilter 실행");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(request); // header에서 jwt를 받아온다.
        String requestURI = request.getRequestURI();
        log.info(">>> doFilter jwt : " + jwt);
        log.info(">>> doFilter requestURI : " + requestURI);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // token이 유효하다면 토큰으로부터 Autentication을 받아와 SecurityContext에 저장한다.
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증정보를 저장했습니다. >>> uri : {}", authentication.getName(), requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다. >>> uri : {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // header에서 토큰 정보를 꺼내온다
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
