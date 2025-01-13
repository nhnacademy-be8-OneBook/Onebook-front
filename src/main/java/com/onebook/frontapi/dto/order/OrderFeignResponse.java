package com.onebook.frontapi.dto.order;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderFeignResponse {
    private long orderId;
    private String orderName;
    private String orderer;
    private LocalDateTime dateTime;
    private int totalPrice;
    private String orderStatusName;
}
