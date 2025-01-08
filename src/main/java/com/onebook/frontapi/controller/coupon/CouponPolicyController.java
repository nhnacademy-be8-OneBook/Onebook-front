package com.onebook.frontapi.controller.coupon;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddPricePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForBookRequest;
import com.onebook.frontapi.dto.coupon.request.couponPolicy.AddRatePolicyForCategoryRequest;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForCategoryResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForCategoryResponse;
import com.onebook.frontapi.service.coupon.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @GetMapping("/coupon-policies/rate-policies-for-book/list")
    public String getRatePolicyForBookList()
    {
        return "coupon/policy-list/rate-policy-for-book-list";
    }

    @GetMapping("/coupon-policies/rate-policy-for-category/list")
    public String getRatePolicyForCategoryList()
    {
        return "coupon/policy-list/rate-policy-for-category-list";
    }

    @GetMapping("/coupon-policies/price-policy-for-book/list")
    public String getPricePolicyForBookList()
    {
        return "coupon/policy-list/price-policy-for-book-list";
    }

    @GetMapping("/coupon-policies/price-policy-for-category/list")
    public String getPricePolicyForCategoryList()
    {
        return "coupon/policy-list/price-policy-for-category-list";
    }

    // 쿠폰 정책 등록
    // 정률정책forBook 등록 폼
    @GetMapping("/coupon-policies/rate/book/register")
    public String getRatePolicyForBookRegisterForm(){
        return "coupon/policy-register/rate-policy-for-book-register-form";
    }

    // 정률정책forCategory 등록 폼
    @GetMapping("/coupon-policies/rate/category/register")
    public String getRatePolicyForCategoryRegisterForm(Model model){

        List<CategoryDTO> categoriesForSelect = couponPolicyService.getCategoriesForSelect();
        model.addAttribute("categoriesForSelect",categoriesForSelect);
        return "coupon/policy-register/rate-policy-for-category-register-form";
    }

    // 정액정책forBook 등록 폼
    @GetMapping("/coupon-policies/price/book/register")
    public String getPricePolicyForBookRegisterForm(){

        return "coupon/policy-register/price-policy-for-book-register-form";
    }
    // 정액정책forCategory 등록 폼
    @GetMapping("/coupon-policies/price/category/register")
    public String getPricePolicyForCategoryRegisterForm(Model model){

        List<CategoryDTO> categoriesForSelect = couponPolicyService.getCategoriesForSelect();
        model.addAttribute("categoriesForSelect",categoriesForSelect);
        return "coupon/policy-register/price-policy-for-category-register-form";
    }

    // 정률정책 for Book 등록
    @PostMapping("/coupon-policies/rate/book/register")
    public String registerRatePolicyForBook(@ModelAttribute AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        couponPolicyService.registerRatePolicyForBook(addRatePolicyForBookRequest);
        return "redirect:/coupon-policies/rate-policies-for-book/list";
    }

    // 정률정책 for category 등록
    @PostMapping("/coupon-policies/rate/category/register")
    public String registerRatePolicyForCategory(@ModelAttribute AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){

        couponPolicyService.registerRatePolicyForCategory(addRatePolicyForCategoryRequest);
        return "redirect:/coupon-policies/rate-policies-for-book/list";
    }

    // 정액정책 for Book 등록
    @PostMapping("/coupon-policies/price/book/register")
    public String registerPricePolicyForBook(@ModelAttribute AddPricePolicyForBookRequest addPricePolicyForBookRequest){

        couponPolicyService.registerPricePolicyForBook(addPricePolicyForBookRequest);
        return "redirect:/coupon-policies/price-policies-for-book/list";
    }

    // 정액정책 for Category 등록
    @PostMapping("/coupon-policies/price/category/register")
    public String registerPricePolicyForCategory(@ModelAttribute AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){

        couponPolicyService.registerPricePolicyForCategory(addPricePolicyForCategoryRequest);
        return "redirect:/coupon-policies/price-policy-for-category/list";
    }

    // 정률정책 for Book 조회
    @GetMapping("/coupon-policies/rate/book")
    public String getRatePoliciesForBook
    (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo, Model model){
        Page<RatePolicyForBookResponse> policyList = couponPolicyService.getRatePoliciesForBook(pageNo);
        model.addAttribute("policyList",policyList);
        return "coupon/policy-list/rate-policy-for-book-list";
    }

    // 정률정책 for Category 조회
    @GetMapping("/coupon-policies/rate/category")
    public String getRatePoliciesForCategory
    (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo, Model model){
        Page<RatePolicyForCategoryResponse> policyList = couponPolicyService.getRatePoliciesForCategory(pageNo);

        model.addAttribute("policyList",policyList.getContent());
        model.addAttribute("totalPage",policyList.getTotalPages());

        return "coupon/policy-list/rate-policy-for-category-list";
    }

    // 정액정책 for Book 조회
    @GetMapping("/coupon-policies/price/book")
    public String getPricePoliciesForBook
    (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo, Model model){
        Page<PricePolicyForBookResponse> policyList = couponPolicyService.getPricePoliciesForBook(pageNo);
        model.addAttribute("policyList",policyList);
        return "coupon/policy-list/price-policy-for-book-list";
    }

    // 정액정책 for Category 조회
    @GetMapping("/coupon-policies/price/category")
    public String getPricePoliciesForCategory
    (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo, Model model){
        Page<PricePolicyForCategoryResponse> policyList = couponPolicyService.getPricePoliciesForCategory(pageNo);
        model.addAttribute("policyList",policyList);
        return "coupon/policy-list/price-policy-for-category-list";
    }
}
