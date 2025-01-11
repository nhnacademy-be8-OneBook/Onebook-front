package com.onebook.frontapi.feign.coupon;

import com.onebook.frontapi.dto.coupon.response.coupon.CouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CouponClient",  url = "${onebook.gatewayUrl}")
public interface CouponClient {

    @GetMapping("task/coupon")
    ResponseEntity<Page<CouponResponse>> getAllCoupons(Pageable pageable);

    @DeleteMapping("task/coupon/{coupon-number}")
    ResponseEntity<CouponResponse> deleteCoupon(@PathVariable(name = "coupon-number") String couponNumber);


}
