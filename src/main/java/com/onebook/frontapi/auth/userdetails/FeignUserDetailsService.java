package com.onebook.frontapi.auth.userdetails;

import com.onebook.frontapi.dto.member.MemberLoginRequestDto;
import com.onebook.frontapi.dto.member.MemberLoginResponseDto;
import com.onebook.frontapi.feign.member.MemberFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeignUserDetailsService implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;

    // 로그인 때 동작.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ResponseEntity<MemberLoginResponseDto> memberLoginResponseDtoResponseEntity = memberFeignClient.loadByMemberId(username);

            MemberLoginResponseDto memberLoginResponseDto = memberLoginResponseDtoResponseEntity.getBody();

            log.info("loginId{}, password{}, role{}", memberLoginResponseDto.loginId(), memberLoginResponseDto.password(), memberLoginResponseDto.role());

            return new User(memberLoginResponseDto.loginId(), memberLoginResponseDto.password(), List.of(new SimpleGrantedAuthority("ROLE_" + memberLoginResponseDto.role())));

        }catch(FeignException e) {
            throw new IllegalArgumentException("존재하지 않는 로그인 ID 입니다.");
        }
    }
}
