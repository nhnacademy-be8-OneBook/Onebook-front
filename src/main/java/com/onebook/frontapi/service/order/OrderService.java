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

    public void createOrder(OrderRegisterResponseDto orderRegisterResponseDto) {
        // TODO 배송비 입력
        int deliveryPrice = 3000;
        int totalPrice = 15000;

        System.out.println("진입!!!!!!!");

        OrderRegisterDto orderRegisterDto = new OrderRegisterDto(
                orderRegisterResponseDto.getOrdererName(),
                orderRegisterResponseDto.getOrdererPhone(),
                LocalDateTime.now(),
                deliveryPrice,
                totalPrice);

        orderClient.createOrder(orderRegisterDto);
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

    public List<String> getAllOrderStatuses() {
        return orderClient.findAllOrderStatuses();
    }

    public List<OrderByStatusResponseDto> getOrdersByStatus(String status) {
        return orderClient.findOrderByStatus(status);
    }
}
