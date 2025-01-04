package com.onebook.frontapi.auth.userdetails;

import com.onebook.frontapi.dto.member.MemberLoginDto;
import com.onebook.frontapi.feign.member.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO 여기 열기 Dev일 때 Inmemeory UserDetails

@Service
//@Profile("!dev")
@RequiredArgsConstructor
public class FeignUserDetailsService implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseEntity<MemberLoginDto> memberLoginDtoResponseEntity = memberFeignClient.loadByMemberId(username);

        MemberLoginDto body = memberLoginDtoResponseEntity.getBody();

        if(body != null){
            return new User(body.loginId(), body.password(), List.of(new SimpleGrantedAuthority("User")));
        }

        throw new RuntimeException();
    }
}
