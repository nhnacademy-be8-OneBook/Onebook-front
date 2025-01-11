package com.onebook.frontapi.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderRequest {
    int bookId;
    int quantity;
    int price;
}
