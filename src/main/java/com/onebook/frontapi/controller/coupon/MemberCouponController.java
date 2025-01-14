package com.onebook.frontapi.controller.coupon;

import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponResponse;
import com.onebook.frontapi.service.coupon.CouponBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberCouponController {

    private final CouponBoxService couponBoxService;

    @GetMapping("/coupon/coupon-box")
    public String getMyCouponBox(Model model,
                                 @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo){

        Page<IssuedCouponResponse> issuedCouponResponses = couponBoxService.getIssuedCouponsByMemberId(pageNo);
        model.addAttribute("issuedCouponResponses",issuedCouponResponses);

        return "coupon/coupon/my-coupon-list";
    }

}
