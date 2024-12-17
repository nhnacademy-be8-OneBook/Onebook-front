package com.onebook.frontapi.auth.feign;

import com.onebook.frontapi.auth.dto.Member;
import com.onebook.frontapi.auth.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "member", url="http://localhost:8510/task/members")
public interface MemberClient {

    @GetMapping("/jwt/joo")
    Member test();
}
