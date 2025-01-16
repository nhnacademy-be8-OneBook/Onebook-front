package com.onebook.frontapi.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderRequest {
    long bookId;
    int quantity;
    int price;
}
