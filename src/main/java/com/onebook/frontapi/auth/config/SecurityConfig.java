package com.onebook.frontapi.auth.config;

import com.onebook.frontapi.auth.handler.AuthSuccessHandler;
import com.onebook.frontapi.feign.auth.AuthFeignClient;
import com.onebook.frontapi.feign.member.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberFeignClient memberFeignClient;
    private final AuthFeignClient authFeignClient;

//    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login") // 사용자 정의 로그인 페이지
                        .usernameParameter("id") // 사용자명 파라미터 이름
                        .passwordParameter("password") // 비밀번호 파라미터 이름
                        .loginProcessingUrl("/login/process") // 로그인 처리 URL
                        .successHandler(new AuthSuccessHandler(authFeignClient))// jwt token 추가하기
//                        .successForwardUrl("/")
                        .permitAll() // 로그인 페이지 접근 허용
        );


        // 개발 할 때는 꺼놓기
//        http.authorizeHttpRequests(authRequest -> {
//            authRequest.requestMatchers("/auth/login", "/public/**").permitAll()
//                    .anyRequest().authenticated();
//        });

        // 개발 중일 때
        http.authorizeHttpRequests( authRequest -> authRequest.anyRequest().permitAll());

        // logout 시 쿠키 삭제하기
        http.logout( logout -> {
            logout.logoutUrl("/auth/logout").
                    deleteCookies("Authorization");
        });


        return http.build();
    }



    // Password Encoder, InMemory is Dev
    @Bean
    @Profile("dev")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 사용
    }

    @Bean
    @Profile("dev")
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        // 사용자 정보 생성
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("password1")) // 비밀번호 인코딩
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password2"))
                .roles("ADMIN")
                .build();

        // InMemoryUserDetailsManager에 사용자 추가
        return new InMemoryUserDetailsManager(user1, admin);
    }



}
