package com.onebook.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderItemDto {
    private Long bookId;
    private int quantity;
}
