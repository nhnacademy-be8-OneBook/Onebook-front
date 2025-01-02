package com.onebook.frontapi.service.order;

import com.onebook.frontapi.dto.order.OrderCreateDTO;
import com.onebook.frontapi.dto.order.OrderRequestDTO;
import com.onebook.frontapi.feign.order.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;

    public void createOrder(OrderRequestDTO orderRequestDTO) {
        // TODO 배송비 입력
        int deliveryPrice = 3000;
        int totalPrice = 15000;

        System.out.println("진입!!!!!!!");

        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(
                orderRequestDTO.getOrdererName(),
                orderRequestDTO.getOrdererPhone(),
                LocalDateTime.now(),
                deliveryPrice,
                totalPrice);

        orderClient.createOrder(659219841608987391L, orderCreateDTO);
    }
}
