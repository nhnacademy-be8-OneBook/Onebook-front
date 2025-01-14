package com.onebook.frontapi.feign.coupon;

import com.onebook.frontapi.dto.coupon.request.coupon.CreateCouponRequest;
import com.onebook.frontapi.dto.coupon.response.coupon.CouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CouponClient",  url = "${onebook.gatewayUrl}")
public interface CouponClient {

    @GetMapping("task/coupon")
    ResponseEntity<Page<CouponResponse>> getAllCoupons(Pageable pageable);

    @GetMapping("/task/coupon/{coupon-number}")
    ResponseEntity<CouponResponse> getCouponByCouponNumber(@PathVariable(name = "coupon-number") String couponNumber);

    @DeleteMapping("task/coupon/{coupon-number}")
    ResponseEntity<CouponResponse> deleteCoupon(@PathVariable(name = "coupon-number") String couponNumber);

    @PostMapping("task/coupon/rate/book")
    ResponseEntity<List<CouponResponse>> createRateCouponForBook(@RequestBody CreateCouponRequest createCouponRequest);

    @PostMapping("task/coupon/rate/category")
    ResponseEntity<List<CouponResponse>> createRateCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest);

    @PostMapping("task/coupon/price/book")
    ResponseEntity<List<CouponResponse>> createPriceCouponForBook(@RequestBody CreateCouponRequest createCouponRequest);

    @PostMapping("task/coupon/price/category")
    ResponseEntity<List<CouponResponse>> createPriceCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest);
}
