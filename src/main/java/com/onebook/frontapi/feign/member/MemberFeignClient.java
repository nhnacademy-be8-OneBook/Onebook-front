package com.onebook.frontapi.feign.member;

import com.onebook.frontapi.dto.member.MemberLoginDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


// TODO task-service-dev -> task-service-prod로 바꿔줘야함
//  여기는 Eureka 사용 안해서 url을 직접 넣어줘야 함 .
@FeignClient(name = "task-service-dev", url = "${onebook.gatewayUrl}")
@Component
public interface MemberFeignClient {
    @GetMapping("/task/auth/members")
    public ResponseEntity<MemberLoginDto> loadByMemberId(String loginId);

}
