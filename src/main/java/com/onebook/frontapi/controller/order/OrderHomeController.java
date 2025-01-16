package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.order.OrderDetailResponse;
import com.onebook.frontapi.dto.order.OrderResponse;
import com.onebook.frontapi.service.order.OrderService;
import com.onebook.frontapi.service.order.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String myOrders(Pageable pageable, Model model) {
        // 내림차순 정렬을 위해 Sort 설정
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("orderId")));

        model.addAttribute("orderList", orderService.getAllOrders(sortedPageable));
        model.addAttribute("orderStatusList", orderStatusService.getAllOrderStatuses());
        return "order/my-orders";
    }

    @GetMapping("/my/orders/waiting")
    public String myWaitingOrders(Pageable pageable, Model model) {
        // 내림차순 정렬을 위해 Sort 설정
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("orderId")));

        Page<OrderResponse> waitingOrders = orderService.getWaitingOrders(pageable);
        model.addAttribute("waitingOrders", waitingOrders);

//        model.addAttribute("orderList", orderService.getAllOrders(sortedPageable));
        model.addAttribute("orderStatusList", orderStatusService.getAllOrderStatuses());
        return "order/my-waiting-orders";
    }


    @GetMapping("/my/order-detail/{orderId}")
    public String myOrderDetail(@PathVariable("orderId") long orderId, Model model) {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderId);

//        model.addAttribute("orderResponse", orderDetail.getOrderResponse());
        model.addAttribute("orderDetail", orderDetail);

        return "order/order-detail";
    }
}
