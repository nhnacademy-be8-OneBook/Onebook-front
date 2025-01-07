package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class OrderHomeController {

    private final OrderService orderService;

    @GetMapping("/my/orders")
    public String myOrders(Model model) {
        model.addAttribute("orderList", orderService.getAllOrders());
        model.addAttribute("orderStatusList", orderService.getAllOrderStatuses());
        return "order/my-orders";
    }

//    @GetMapping("/my/orders")
    public String myOrders(Model model, @PathVariable String orderStatusName) {
//        model.addAttribute("orderList", orderService.getOrders());
        model.addAttribute("orderStatusList", orderService.getAllOrderStatuses());
        return "order/my-orders";
    }
}
