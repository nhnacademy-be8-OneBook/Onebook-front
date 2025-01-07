package com.onebook.frontapi.dto.coupon.request.couponPolicy;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AddPricePolicyForCategoryRequest {
    @NotNull
    private Integer minimumOrderAmount;
    @NotNull
    private Integer discountPrice;
    @NotNull
    private LocalDateTime expirationPeriodStart;
    @NotNull
    private LocalDateTime expirationPeriodEnd;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer policyStatusId;
}
