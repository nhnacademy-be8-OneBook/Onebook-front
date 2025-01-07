package com.onebook.frontapi.auth.config;

import com.onebook.frontapi.auth.filter.JwtAuthFilter;
import com.onebook.frontapi.auth.handler.AuthSuccessHandler;
import com.onebook.frontapi.auth.handler.CustomAccessDeniedHandler;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final AuthFeignClient authFeignClient;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

//        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
//                SessionCreationPolicy.STATELESS));


        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .usernameParameter("id") // 사용자명 파라미터 이름
                        .passwordParameter("password") // 비밀번호 파라미터 이름
                        .loginProcessingUrl("/login/process") // 로그인 처리 URL
                        .successHandler(new AuthSuccessHandler(authFeignClient))// jwt token 추가하기
                        .permitAll() // 로그인 페이지 접근 허용
        );

        http.addFilterAt(new JwtAuthFilter(authFeignClient), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authRequest -> {
            authRequest.requestMatchers("/", "/login", "/public/**",
                            "/css/**", "/js/**", "/images/**", "/join", "/test/**",
                            "/style.css").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 로그인할 때 Authentication을 생성하는데 이때 ROLE을 넣어줬음. 그래서 이렇게 사용 가능.
                    .anyRequest().authenticated();
        });

        // logout 시 쿠키 삭제하기
        http.logout(logout -> {
            logout.logoutUrl("/logout").
                    deleteCookies("Authorization");
        });

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
        );

        return http.build();
    }

    // Password Encoder, InMemory is Dev
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 사용

    }

}
