package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.dto.book.BookDTO;
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

    @GetMapping("/order/home")
    public String home(Model model) {
        Map<BookDTO, Integer> bookMap = new HashMap<>();

        model.addAttribute("bookMap", bookMap);
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/order";
    }

    @GetMapping("/my/orders")
    public String myOrders(Model model) {
        model.addAttribute("orderList", orderService.getAllOrders());
        model.addAttribute("orderStatusList", orderStatusService.getAllOrderStatuses());
        return "order/my-orders";
    }

//    @GetMapping("/my/orders")
    public String myOrders(Model model, @PathVariable String orderStatusName) {
//        model.addAttribute("orderList", orderService.getOrders());
        model.addAttribute("orderStatusList", orderStatusService.getAllOrderStatuses());
        return "order/my-orders";
    }
}
