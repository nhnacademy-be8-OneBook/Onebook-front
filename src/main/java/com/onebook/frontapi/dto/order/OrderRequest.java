package com.onebook.frontapi.dto.order;

import com.onebook.frontapi.dto.book.BookOrderRequest;
import com.onebook.frontapi.dto.delivery.DeliveryRequest;
import com.onebook.frontapi.dto.packaging.PackagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private List<BookOrderRequest> items;
//    private DeliveryRequest delivery;
//    private PackagingRequest packaging;
}
