package com.onebook.frontapi.feign.order;

import com.onebook.frontapi.dto.order.OrderCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "OrderClient", url = "${onebook.gatewayUrl}")
public interface OrderClient {
    @PostMapping("/task/order")
    void createOrder(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody OrderCreateDTO orderCreateDTO);

}