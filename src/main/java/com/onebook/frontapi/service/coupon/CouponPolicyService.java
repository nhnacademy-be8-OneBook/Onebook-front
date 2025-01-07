package com.onebook.frontapi.service.coupon;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForCategoryResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForCategoryResponse;
import com.onebook.frontapi.feign.category.CategoryClient;
import com.onebook.frontapi.feign.coupon.CouponPolicyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {

    private final CategoryClient categoryClient;
    private final CouponPolicyClient couponPolicyClient;

    public RatePolicyForBookResponse registerRatePolicyForBook(AddRatePolicyForBookRequest addRatePolicyForBookRequest){
        return couponPolicyClient.addRatePolicyForBook(addRatePolicyForBookRequest).getBody();
    }


    public RatePolicyForCategoryResponse registerRatePolicyForCategory(AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){
        return couponPolicyClient.addRatePolicyForCategory(addRatePolicyForCategoryRequest).getBody();
    }


    public PricePolicyForBookResponse registerPricePolicyForBook(AddPricePolicyForBookRequest addPricePolicyForBookRequest){
        return couponPolicyClient.addPricePolicyForBook(addPricePolicyForBookRequest).getBody();
    }

    public PricePolicyForCategoryResponse registerPricePolicyForCategory(AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){
        return couponPolicyClient.addPricePolicyForCategory(addPricePolicyForCategoryRequest).getBody();
    }

    public List<CategoryDTO> getCategoriesForSelect(){
        return categoryClient.getCategories().getBody();
    }
}
