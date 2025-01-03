package com.onebook.frontapi.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class CheckOutController {

    @GetMapping("/front/payments/checkout-page")
    public String showCheckout(@RequestParam String orderId, Model model) {
        return "payment/checkout";
    }
}
