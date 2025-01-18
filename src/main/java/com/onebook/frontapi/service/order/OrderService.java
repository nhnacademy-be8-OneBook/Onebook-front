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

    public OrderMemberResponse getOrder(Long orderId) {
        OrderMemberFeignResponse body = orderClient.findOrderByOrderId(orderId).getBody();
        // TODO 에러처리
        if (body == null) {
            throw new RuntimeException();
        }
        return OrderMemberResponse.fromFeign(body);
    }

    public Page<OrderResponse> getOrders(String orderStatus, Pageable pageable) {
        Page<OrderFeignResponse> body = orderClient.findOrders(orderStatus, pageable).getBody();
        return body.map(OrderResponse::fromFeign);
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
