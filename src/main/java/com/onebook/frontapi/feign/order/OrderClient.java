package com.onebook.frontapi.feign.order;

import com.onebook.frontapi.dto.order.OrderByStatusResponseDto;
import com.onebook.frontapi.dto.order.OrderRegisterDto;
import com.onebook.frontapi.dto.order.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "OrderClient", url = "${onebook.gatewayUrl}")
public interface OrderClient {
    // 주문 생성
    @PostMapping("/task/order")
    void createOrder(@RequestBody OrderRegisterDto orderRegisterDto);

    // 사용자의 모든 주문 불러오기
    @GetMapping("/task/orders")
    List<OrderRequestDto> findAllOrders();

    // 주문 상태에 따른 주문 리스트 불러오기
    @GetMapping("/task/admin/orders")
    List<OrderByStatusResponseDto> findOrderByStatus(@RequestParam("status") String status);

    // 주문 상태 변경하기
    @PutMapping("/task/admin/orders")
    void updateOrderStatus(@RequestBody List<Long> orderIds, @RequestParam String status);

}