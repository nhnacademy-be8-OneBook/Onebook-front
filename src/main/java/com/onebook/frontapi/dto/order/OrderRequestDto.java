package com.onebook.frontapi.dto.order;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderRequestDto {
    String orderer;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
    String orderStatusName;
}
