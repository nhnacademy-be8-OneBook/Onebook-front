package com.onebook.frontapi.service.join;

import com.onebook.frontapi.adaptor.member.MemberAdaptor;
import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final MemberAdaptor memberAdaptor;

    /**
     * 회원 가입
     **/
    public boolean memberJoin(MemberRegisterRequestDto memberRegisterRequestDto) {
        if(memberAdaptor.register(memberRegisterRequestDto)) {
            return true;
        }
        return false;
    }

    /**
    * 두레이 메시지 인증
    **/


}
