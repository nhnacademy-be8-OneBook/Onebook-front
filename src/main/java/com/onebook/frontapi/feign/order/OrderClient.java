package com.onebook.frontapi.feign.order;

import com.onebook.frontapi.dto.order.OrderRegisterDto;
import com.onebook.frontapi.dto.order.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "OrderClient", url = "${onebook.gatewayUrl}")
public interface OrderClient {
    @PostMapping("/task/order")
    void createOrder(@RequestBody OrderRegisterDto orderRegisterDto);

    @GetMapping("/task/orders")
    List<OrderRequestDto> findAllOrders();

    @GetMapping("/task/order/statuses")
    List<String> findAllOrderStatuses();

}