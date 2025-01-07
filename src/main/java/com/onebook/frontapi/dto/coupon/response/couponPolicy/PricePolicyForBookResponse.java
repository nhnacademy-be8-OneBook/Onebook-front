package com.onebook.frontapi.dto.coupon.response.couponPolicy;

import java.time.LocalDateTime;

public class PricePolicyForBookResponse {
    private Long id;
    private Integer minimumOrderAmount;
    private Integer discountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String bookName;
    private String bookIsbn13;
    private String policyStatusName;
}
