package com.onebook.frontapi.auth.handler;

import com.onebook.frontapi.adaptor.member.MemberAdaptor;
import com.onebook.frontapi.dto.auth.JwtLoginIdRequest;
import com.onebook.frontapi.dto.auth.TokenResponse;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import com.onebook.frontapi.feign.member.MemberFeignClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthFeignClient authFeignClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("success handler call ");
        log.info("authentication :{}", authentication);
        User principal = (User)authentication.getPrincipal();

        String username = principal.getUsername();
        log.info("username : {}", username);

        // TODO - jwt
        // 여기서 FeignClient로 Auth 서버에 보냄
        JwtLoginIdRequest jwtLoginIdRequest = new JwtLoginIdRequest();
        jwtLoginIdRequest.setId(username);

        ResponseEntity<TokenResponse> jwtToken = authFeignClient.getJwtToken(username);
        log.info("jwt token AccessToken : {}", jwtToken.getBody().getAccessToken());
        log.info("jwt token tokenType : {}", jwtToken.getBody().getTokenType());
        log.info("jwt token ExpiredIn : {}", jwtToken.getBody().getExpiredIn());
//          cookie

        // cookie 이름 바꾸기
        Cookie cookie = new Cookie("Authorization",
                 jwtToken.getBody().getAccessToken()
        );

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtToken.getBody().getExpiredIn());

        response.addCookie(cookie);

//        response.addHeader("Authorization", jwtToken.getBody().getTokenType() + " " + jwtToken.getBody().getAccessToken());
        response.sendRedirect("/");
    }
}
