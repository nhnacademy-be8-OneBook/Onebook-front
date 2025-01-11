package com.onebook.frontapi.dto.order;

import java.util.List;

public class ProductOrderDto {
    private List<ProductOrderItemDto> items;

    public List<ProductOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<ProductOrderItemDto> items) {
        this.items = items;
    }
}
