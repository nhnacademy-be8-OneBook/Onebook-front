package com.onebook.frontapi.dto.cart;

public record BookOrderRequest(
        Long bookId,
        int quantity
) {
}
