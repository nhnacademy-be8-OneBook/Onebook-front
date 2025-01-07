package com.onebook.frontapi.dto.coupon.response.couponPolicy;

import java.time.LocalDateTime;

public class RatePolicyForCategoryResponse {
    private Long id;
    private Integer discountRate;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String categoryName;
    private String policyStatusName;
}
