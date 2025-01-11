package com.onebook.frontapi.dto.cart;

import java.util.ArrayList;
import java.util.List;

public record CartResponse(
        String cartId,
        Long memberId,
        List<CartItemResponse> cartItemResponses
) {
    // CartFeignResponse -> CartResponse
    public static CartResponse from(CartFeignResponse cartFeignResponse) {
        return new CartResponse(
                cartFeignResponse.cartId(),
                cartFeignResponse.memberId(),
                CartResponse.toCartItems(cartFeignResponse.cartItemFeignResponseList())
        );
    }

    public static List<CartItemResponse> toCartItems(List<CartItemFeignResponse> cartItemFeignResponses) {
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        for(CartItemFeignResponse cir : cartItemFeignResponses) {
            CartItemResponse ci = CartItemResponse.fromCartItemResponseDto(cir);
            cartItemResponseList.add(ci);
        }

        return cartItemResponseList;
    }

}
