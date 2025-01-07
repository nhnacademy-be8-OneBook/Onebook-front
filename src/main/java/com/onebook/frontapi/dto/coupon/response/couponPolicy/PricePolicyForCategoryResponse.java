package com.onebook.frontapi.dto.coupon.response.couponPolicy;

import java.time.LocalDateTime;

public class PricePolicyForCategoryResponse {
    private Long id;
    private Integer minimumOrderAmount;
    private Integer discountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String categoryName;
    private String policyStatusName;
}
