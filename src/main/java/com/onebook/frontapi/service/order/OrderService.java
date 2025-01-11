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

    public Long createOrder(OrderRegisterResponseDto orderRegisterResponseDto) {
        // TODO 배송비 입력? 백에서 해야되는거 아닌가?
        int deliveryPrice = 3000;
        int totalPrice = 15000;

        OrderRegisterRequest orderRegisterRequest = new OrderRegisterRequest(
                orderRegisterResponseDto.getOrdererName(),
                orderRegisterResponseDto.getOrdererPhone(),
                LocalDateTime.now(),
                deliveryPrice,
                totalPrice);

        return orderClient.createOrder(orderRegisterRequest);
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
