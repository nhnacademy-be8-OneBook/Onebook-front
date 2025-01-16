package com.onebook.frontapi.feign.order;

import com.onebook.frontapi.dto.order.OrderByStatusResponseDto;
import com.onebook.frontapi.dto.order.OrderDetailFeignResponse;
import com.onebook.frontapi.dto.order.OrderFormRequest;
import com.onebook.frontapi.dto.order.OrderFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "OrderClient", url = "${onebook.gatewayUrl}")
public interface OrderClient {
    // 주문 생성
    @PostMapping("/task/order")
    Long createOrder(@RequestBody OrderFormRequest orderFormRequest);

    // 사용자의 모든 주문 불러오기
    @GetMapping("/task/orders")
    Page<OrderFeignResponse> findAllOrders(Pageable pageable);

    // 사용자의 모든 주문 불러오기
    @GetMapping("/task/orders/waiting")
    Page<OrderFeignResponse> findWaitingOrders(Pageable pageable);

    // 주문 상태에 따른 주문 리스트 불러오기
    @GetMapping("/task/admin/orders")
    List<OrderByStatusResponseDto> findOrderByStatus(@RequestParam("status") String status);

    // 주문 상태 변경하기
    @PutMapping("/task/admin/orders")
    void updateOrderStatus(@RequestBody List<Long> orderIds, @RequestParam String status);

    // 주문 상세 정보 확인하기
    @GetMapping("/task/order-detail/{orderId}")
    OrderDetailFeignResponse findOrderDetail(@PathVariable("orderId") Long orderId);
}