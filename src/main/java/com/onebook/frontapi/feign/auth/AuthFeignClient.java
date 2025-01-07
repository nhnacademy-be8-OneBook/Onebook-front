package com.onebook.frontapi.feign.auth;

import com.onebook.frontapi.auth.dto.MemberInfoResponse;
import com.onebook.frontapi.dto.auth.JwtLoginIdRequest;
import com.onebook.frontapi.dto.auth.TokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "${onebook.gatewayUrl}")
@Component
public interface AuthFeignClient {
    @GetMapping("/auth/jwt")
    ResponseEntity<TokenResponse> getJwtToken(@RequestParam("id") String id);

    @GetMapping("/auth/test")
    public String returnString();

    // authorization에 토큰만 담김 Berear 뺐음.
    @GetMapping("/auth/my/info")
    MemberInfoResponse getInfoByAuthorization(@RequestHeader("Authorization") String authorization);


}