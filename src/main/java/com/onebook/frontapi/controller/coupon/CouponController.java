package com.onebook.frontapi.controller.coupon;

import com.onebook.frontapi.dto.coupon.response.coupon.CouponResponse;
import com.onebook.frontapi.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupon")
    public String getAllCouponPage(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo, Model model ){

        Page<CouponResponse> couponList= couponService.getAllCoupons(pageNo);
        model.addAttribute("couponList",couponList);

        return "coupon/coupon/coupon-list";
    }

    @GetMapping("/coupon/rate-for-book/{policy-id}/create")
    public String getRateCouponForBookCreatePage(@PathVariable(name = "policy-id") Long policyId, Model model){

        model.addAttribute("policyId",policyId);
        return "coupon/coupon/rate-coupon-for-book-create-form";
    }

    @GetMapping("/coupon/rate-for-category/{policy-id}/create")
    public String getRateCouponForCategoryCreatePage(@PathVariable(name = "policy-id") Long policyId, Model model){

        model.addAttribute("policyId",policyId);
        return "coupon/coupon/rate-coupon-for-category-create-form";
    }

    @GetMapping("/coupon/price-for-book/{policy-id}/create")
    public String getPriceCouponForBookCreatePage(@PathVariable(name = "policy-id") Long policyId, Model model){

        model.addAttribute("policyId",policyId);
        return "coupon/coupon/price-coupon-for-book-create-form";
    }

    @GetMapping("/coupon/price-for-category/{policy-id}/create")
    public String getPriceCouponForCategoryCreatePage(@PathVariable(name = "policy-id") Long policyId, Model model){

        model.addAttribute("policyId",policyId);
        return "coupon/coupon/price-coupon-for-category-create-form";
    }

    @DeleteMapping("/coupon/{coupon-number}")
    public String deleteCoupon(@PathVariable(name = "coupon-number") String couponNumber){

        couponService.deleteCoupon(couponNumber);
        return "redirect:/coupon";
    }
}
