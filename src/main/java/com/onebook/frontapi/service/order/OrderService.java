package com.onebook.frontapi.service.order;

import com.onebook.frontapi.dto.order.*;
import com.onebook.frontapi.feign.member.MemberClient;
import com.onebook.frontapi.feign.order.OrderClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderClient orderClient;
    private final MemberClient memberClient;

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
        try {
            if (status.equals("구매확정")) {
                for (Long orderId : orderIds) {
                    OrderMemberResponse orderMemberResponse = getOrder(orderId);
                    memberClient.getMemberNetPaymentAmountForAdmin(orderMemberResponse.getMemberId());
                }
            }
        }catch(FeignException e) {
            log.info("순수 구매 금액에 따른 회원 등급 업데이트 실패");
        }
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
