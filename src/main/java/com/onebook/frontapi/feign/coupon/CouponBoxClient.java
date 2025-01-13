package com.onebook.frontapi.feign.coupon;

import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "CouponBoxClient",  url = "${onebook.gatewayUrl}")
public interface CouponBoxClient {

    @PostMapping("/task/coupon/issue/welcome/{login-id}")
    ResponseEntity<IssuedCouponResponse> issueWelcomeCouponToMember(@PathVariable(name = "login-id") String loginId);

}
