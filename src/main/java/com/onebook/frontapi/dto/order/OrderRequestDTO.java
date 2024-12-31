package com.onebook.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderRequestDTO {
    private String packagingName;
    private int packagingPrice;
    private String ordererName;
    private String ordererPhone;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
}