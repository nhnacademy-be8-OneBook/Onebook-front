package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.dto.order.OrderRequestDTO;
import com.onebook.frontapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/register")
    public String orderRegister() {
        return "order/register";
    }

    @PostMapping("/order/register")
    public String submitOrder(@ModelAttribute OrderRequestDTO orderRequestDto, RedirectAttributes redirectAttributes) {
        // 받은 객체로 로직 수행
        System.out.println("포장지: " + orderRequestDto.getPackagingName() + " (" + orderRequestDto.getPackagingPrice() + ")");
        System.out.println("주문인: " + orderRequestDto.getOrdererName() + ", " + orderRequestDto.getOrdererPhone());
        System.out.println("수령인: " + orderRequestDto.getReceiverName() + ", " + orderRequestDto.getReceiverPhone() + ", " + orderRequestDto.getReceiverAddress());

        // 데이터를 리다이렉트할 페이지로 전달
        redirectAttributes.addFlashAttribute("orderSuccess", true);
        redirectAttributes.addFlashAttribute("orderDetails", orderRequestDto);

        orderService.createOrder(orderRequestDto);

        return "redirect:/order/success"; // 등록 성공 페이지로 이동
    }

    @GetMapping("/order/success")
    public String showOrderSuccess(Model model) {
        if (model.containsAttribute("orderSuccess")) {
            // 성공 메시지나 주문 정보 처리
            OrderRequestDTO orderDetails = (OrderRequestDTO) model.getAttribute("orderDetails");
            System.out.println("주문 성공! " + orderDetails);
        }
        return "order/orderSuccess"; // 주문 성공 페이지 반환
    }
}
