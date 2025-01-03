package com.onebook.frontapi.auth.config;

import com.onebook.frontapi.auth.handler.AuthSuccessHandler;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import com.onebook.frontapi.feign.member.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final AuthFeignClient authFeignClient;

    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);


        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login") // 사용자 정의 로그인 페이지
                        .usernameParameter("id") // 사용자명 파라미터 이름
                        .passwordParameter("password") // 비밀번호 파라미터 이름
                        .loginProcessingUrl("/login/process") // 로그인 처리 URL
                        .successHandler(new AuthSuccessHandler(authFeignClient))// jwt token 추가하기
                        .permitAll() // 로그인 페이지 접근 허용
        );
        // dev 외의 환경에서는 제한된 접근만 허용
        http.authorizeHttpRequests(authRequest -> {
            authRequest.requestMatchers("/", "/auth/login", "/public/**",
                            "/css/**", "/js/**", "/images/**", "/join", "/test/**",
                            "/style.css").permitAll()
                    .anyRequest().authenticated();
        });

        // logout 시 쿠키 삭제하기
        http.logout(logout -> {
            logout.logoutUrl("/auth/logout").
                    deleteCookies("Authorization");
        });

        return http.build();
    }


    // Password Encoder, InMemory is Dev
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 사용

    }


}
