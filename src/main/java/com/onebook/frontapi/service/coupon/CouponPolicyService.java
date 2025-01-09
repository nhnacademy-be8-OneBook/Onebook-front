package com.onebook.frontapi.service.coupon;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.*;
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
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<CategoryDTO> categories = categoryClient.getCategories().getBody();
        List<CategoryDTO> existCategories = new ArrayList<>();
        for(CategoryDTO categoryDTO : categories){

            if(!categoryDTO.isStatus()){
                existCategories.add(categoryDTO);
            }

        }

        return existCategories;
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

    public RatePolicyForBookResponse getRatePolicyForBook(Long id){
        return couponPolicyClient.getRatePolicyForBook(id).getBody();
    }

    public RatePolicyForCategoryResponse getRatePolicyForCategory(Long id){
        return couponPolicyClient.getRatePolicyForCategory(id).getBody();
    }

    public PricePolicyForBookResponse getPricePolicyForBook(Long id){
        return couponPolicyClient.getPricePolicyForBook(id).getBody();
    }

    public PricePolicyForCategoryResponse getPricePolicyForCategory(Long id){
        return couponPolicyClient.getPricePolicyForCategory(id).getBody();
    }

    public RatePolicyForBookResponse updateRatePolicyForBook(UpdateRatePolicyForBookRequest updateRatePolicyForBookRequest){
        return couponPolicyClient.updateRatePolicyForBook(updateRatePolicyForBookRequest).getBody();
    }

    public RatePolicyForCategoryResponse updateRatePolicyForCategory(UpdateRatePolicyForCategoryRequest updateRatePolicyForCategoryRequest){
        return couponPolicyClient.updateRatePolicyForCategory(updateRatePolicyForCategoryRequest).getBody();
    }

    public PricePolicyForBookResponse updatePricePolicyForBook(UpdatePricePolicyForBookRequest updatePricePolicyForBookRequest){
        return couponPolicyClient.updatePricePolicyForBook(updatePricePolicyForBookRequest).getBody();
    }

    public PricePolicyForCategoryResponse updatePricePolicyForCategory(UpdatePricePolicyForCategoryRequest updatePricePolicyForCategoryRequest){
        return couponPolicyClient.updatePricePolicyForCategory(updatePricePolicyForCategoryRequest).getBody();
    }

    public RatePolicyForBookResponse deleteRatePolicyForBook(Long id){
        return couponPolicyClient.deleteRatePolicyForBook(id).getBody();
    }

    public RatePolicyForBookResponse deleteRatePolicyForCategory(Long id){
        return couponPolicyClient.deleteRatePolicyForCategory(id).getBody();
    }

    public RatePolicyForBookResponse deletePricePolicyForBook(Long id){
        return couponPolicyClient.deletePricePolicyForBook(id).getBody();
    }

    public RatePolicyForBookResponse deletePricePolicyForCategory(Long id){
        return couponPolicyClient.deletePricePolicyForCategory(id).getBody();
    }
}
