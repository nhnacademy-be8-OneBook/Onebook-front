package com.onebook.frontapi.service.order;

import com.onebook.frontapi.dto.order.*;
import com.onebook.frontapi.feign.order.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;

    public Long createOrder(OrderFormRequest orderFormRequest) {
        return orderClient.createOrder(orderFormRequest);
    }

    public List<OrderResponseDto> getAllOrders() {
        List<OrderRequestDto> allOrders = orderClient.findAllOrders();
        return allOrders.stream().map(orderRequestDto -> new OrderResponseDto(
                orderRequestDto.getOrderer(),
                orderRequestDto.getDateTime(),
                orderRequestDto.getDeliveryPrice(),
                orderRequestDto.getTotalPrice(),
                orderRequestDto.getOrderStatusName()
        )).toList();
    }

    public List<OrderByStatusResponseDto> getOrdersByStatus(String status) {
        return orderClient.findOrderByStatus(status);
    }

    public void updateOrderStatus(List<Long> orderIds, String status) {
        orderClient.updateOrderStatus(orderIds, status);
    }

}
