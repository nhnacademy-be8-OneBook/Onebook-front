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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {

    private final CategoryClient categoryClient;
    private final CouponPolicyClient couponPolicyClient;
    private final int PAGE_SIZE = 10;

    public RatePolicyForBookResponse registerRatePolicyForBook
            (AddRatePolicyForBookRequest addRatePolicyForBookRequest){
        return couponPolicyClient.addRatePolicyForBook(addRatePolicyForBookRequest).getBody();
    }

    public RatePolicyForCategoryResponse registerRatePolicyForCategory
            (AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){
        return couponPolicyClient.addRatePolicyForCategory(addRatePolicyForCategoryRequest).getBody();
    }

    public PricePolicyForBookResponse registerPricePolicyForBook
            (AddPricePolicyForBookRequest addPricePolicyForBookRequest){
        return couponPolicyClient.addPricePolicyForBook(addPricePolicyForBookRequest).getBody();
    }

    public PricePolicyForCategoryResponse registerPricePolicyForCategory
            (AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){
        return couponPolicyClient.addPricePolicyForCategory(addPricePolicyForCategoryRequest).getBody();
    }

    public List<CategoryDTO> getCategoriesForSelect(){
        return categoryClient.getCategories().getBody();
    }

    public Page<RatePolicyForBookResponse> getRatePoliciesForBook(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return couponPolicyClient.getRatePoliciesForBook(pageable).getBody();
    }

    public Page<RatePolicyForCategoryResponse> getRatePoliciesForCategory(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return couponPolicyClient.getRatePoliciesForCategory(pageable).getBody();
    }

    public Page<PricePolicyForBookResponse> getPricePoliciesForBook(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return couponPolicyClient.getPricePoliciesForBook(pageable).getBody();
    }

    public Page<PricePolicyForCategoryResponse> getPricePoliciesForCategory(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return couponPolicyClient.getPricePoliciesForCategory(pageable).getBody();
    }
}
