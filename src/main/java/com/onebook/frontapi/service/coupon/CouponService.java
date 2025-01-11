package com.onebook.frontapi.service.coupon;

import com.onebook.frontapi.dto.coupon.response.coupon.CouponResponse;
import com.onebook.frontapi.feign.coupon.CouponClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponClient couponClient;
    private final int PAGE_SIZE = 10;

    public Page<CouponResponse> getAllCoupons(int pageNo){

        Pageable pageable = PageRequest.of(pageNo,PAGE_SIZE, Sort.by("creationTime").descending());
        return couponClient.getAllCoupons(pageable).getBody();
    }

    public CouponResponse deleteCoupon(String couponNumber){
        return couponClient.deleteCoupon(couponNumber).getBody();
    }
}
