package com.example.securitypracitce.config;

import com.example.securitypracitce.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .securityContexts(Arrays.asList(securityContext())) // 추가
                .securitySchemes(Arrays.asList(authorizationKey())) // 추가
                .select()
                .apis(RequestHandlerSelectors.any()) // 대상 패키지 설정
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Spring Security Practice API문서") // 문서 제목
                .description("Spring Security Practice 프로젝트") // 문서 설명
                .version("1.0.0")
                .build();
    }

    private ApiKey authorizationKey() {
        return new ApiKey("JWT_TOKEN", JwtFilter.AUTHORIZATION_HEADER, "header"); // JwtFilter.AUTHORIZATION_HEADER = "Authorization" -> 헤더에 jwt 토큰값을 넣는 이름
    }

    // import springfox.documentation.spi.service.contexts.SecurityContext; <- security의 SecurityContext 가 아님! import 주의
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Arrays.asList(new SecurityReference("JWT_TOKEN", authorizationScopes)); // SecurityReference의 두번째 인자가 배열로 받아야해서 이렇게 설정해줌
    }
}
