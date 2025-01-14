package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.order.OrderDetailResponse;
import com.onebook.frontapi.service.order.OrderService;
import com.onebook.frontapi.service.order.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class OrderHomeController {

    private final OrderService orderService;
    private final OrderStatusService orderStatusService;

    @GetMapping("/my/orders")
    public String myOrders(Model model) {
        model.addAttribute("orderList", orderService.getAllOrders());
        model.addAttribute("orderStatusList", orderStatusService.getAllOrderStatuses());
        return "order/my-orders";
    }

    @GetMapping("/my/order-detail/{orderId}")
    public String myOrderDetail(@PathVariable("orderId") long orderId,  Model model) {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderId);

//        model.addAttribute("orderResponse", orderDetail.getOrderResponse());
        model.addAttribute("orderDetail", orderDetail);

        return "order/order-detail";
    }
}
