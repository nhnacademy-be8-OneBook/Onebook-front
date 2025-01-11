package com.onebook.frontapi.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookOrderRequest {
    int bookId;
    int quantity;
    int price;
}
