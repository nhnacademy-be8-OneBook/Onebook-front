package com.onebook.frontapi.auth.filter;

import com.onebook.frontapi.auth.dto.MemberInfoResponse;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private  final AuthFeignClient authFeignClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies) {
            if(c.getName().equals("Authorization")) {
               MemberInfoResponse memberInfoResponse = authFeignClient.getInfoByAuthorization("Bearer " + c.getValue());

               Authentication authentication = new UsernamePasswordAuthenticationToken(
                       memberInfoResponse.loginId(),
                       null,
                       List.of(new SimpleGrantedAuthority("ROLE_" + memberInfoResponse.role()))
               );

               SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);


    }
}