package com.onebook.frontapi.service.order;

import com.onebook.frontapi.dto.order.*;
import com.onebook.frontapi.feign.order.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;

    public Long createOrder(OrderFormRequest orderFormRequest) {
        return orderClient.createOrder(orderFormRequest);
    }

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderClient.findAllOrders(pageable).map(OrderResponse::fromFeign);
    }

    public List<OrderByStatusResponseDto> getOrdersByStatus(String status) {
        return orderClient.findOrderByStatus(status);
    }

    public void updateOrderStatus(List<Long> orderIds, String status) {
        orderClient.updateOrderStatus(orderIds, status);
    }

    public OrderDetailResponse getOrderDetail(Long orderId) {
        OrderDetailFeignResponse orderDetail = orderClient.findOrderDetail(orderId);

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse(
            orderDetail.getItems(),
            OrderResponse.fromFeign(orderDetail.getOrderResponse())
        );

        return orderDetailResponse;
    }

}
