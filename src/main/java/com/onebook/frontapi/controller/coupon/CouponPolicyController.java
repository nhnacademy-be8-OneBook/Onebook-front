package com.onebook.frontapi.controller.coupon;

import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForBookRequest;
import com.onebook.frontapi.service.coupon.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @GetMapping("/coupon-policies/rate-policies-for-book/list")
    public String getRatePolicyForBookList()
    {
        return "/coupon/policy-list/rate-policy-for-book-list";
    }

    @GetMapping("/coupon-policies/rate-policy-for-category/list")
    public String getRatePolicyForCategoryList()
    {
        return "/coupon/policy-list/rate-policy-for-category-list";
    }

    @GetMapping("/coupon-policies/price-policy-for-book/list")
    public String getPricePolicyForBookList()
    {
        return "/coupon/policy-list/price-policy-for-book-list";
    }

    @GetMapping("/coupon-policies/price-policy-for-category/list")
    public String getPricePolicyForCategoryList()
    {
        return "/coupon/policy-list/price-policy-for-category-list";
    }

    // 쿠폰 정책 등록
    // 정책 등록 폼
    @GetMapping("/coupon-policies/rate/book/register")
    public String getRatePolicyForBookRegisterForm(){
        return "/coupon/policy-register/rate-policy-for-book-register-form";
    }

    // 정책 등록
    @PostMapping("/coupon-policies/rate/book/register")
    public String registerRatePolicyForBook(@ModelAttribute AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        couponPolicyService.registerRatePolicyForBook(addRatePolicyForBookRequest);
        return "redirect:/coupon-policies/rate-policies-for-book/list";
    }




}
