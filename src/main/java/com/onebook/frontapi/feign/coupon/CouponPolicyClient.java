package com.onebook.frontapi.feign.coupon;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForCategoryResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CouponPolicyClient",  url = "${onebook.gatewayUrl}")
public interface CouponPolicyClient {

    @PostMapping("task/policies/rate/book")
    ResponseEntity<RatePolicyForBookResponse> addRatePolicyForBook
            (@RequestBody AddRatePolicyForBookRequest addRatePolicyForBookRequest);

    @PostMapping("task/policies/rate/category")
    ResponseEntity<RatePolicyForCategoryResponse> addRatePolicyForCategory
            (@RequestBody AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest);

    @PostMapping("task/policies/price/book")
    ResponseEntity<PricePolicyForBookResponse> addPricePolicyForBook
            (@RequestBody AddPricePolicyForBookRequest addPricePolicyForBookRequest);

    @PostMapping("task/policies/price/category")
    ResponseEntity<PricePolicyForCategoryResponse> addPricePolicyForCategory
            (@RequestBody AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest);

    @GetMapping("task/policies/rate/book")
    ResponseEntity<Page<RatePolicyForBookResponse>> getRatePoliciesForBook(Pageable pageable);

    @GetMapping("task/policies/rate/category")
    ResponseEntity<Page<RatePolicyForCategoryResponse>> getRatePoliciesForCategory(Pageable pageable);

    @GetMapping("task/policies/price/book")
    ResponseEntity<Page<PricePolicyForBookResponse>> getPricePoliciesForBook(Pageable pageable);

    @GetMapping("task/policies/price/category")
    ResponseEntity<Page<PricePolicyForCategoryResponse>> getPricePoliciesForCategory(Pageable pageable);
}
