package com.onebook.frontapi.dto.order;

import lombok.Getter;

@Getter
public class OrderDetailBookFeignResponse {
    private String bookTitle;
    private int quantity;
    private int price;
}
