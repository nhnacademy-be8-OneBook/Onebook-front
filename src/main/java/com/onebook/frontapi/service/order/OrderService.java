package com.onebook.frontapi.service.order;

import com.onebook.frontapi.dto.order.*;
import com.onebook.frontapi.feign.order.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;

    public Long createOrder(OrderFormRequest orderFormRequest) {
        return orderClient.createOrder(orderFormRequest);
    }

    public List<OrderResponse> getAllOrders() {
        return orderClient.findAllOrders().stream().map(OrderResponse::fromFeign).toList();
    }

    public List<OrderByStatusResponseDto> getOrdersByStatus(String status) {
        return orderClient.findOrderByStatus(status);
    }

    public void updateOrderStatus(List<Long> orderIds, String status) {
        orderClient.updateOrderStatus(orderIds, status);
    }

}
