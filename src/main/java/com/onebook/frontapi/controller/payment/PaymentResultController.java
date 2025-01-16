package com.onebook.frontapi.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentResultController {

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam String amount,
            Model model
    ) {
        // 필요한 로직 or DB 업데이트 / 보여줄 모델 데이터
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "payment/success";
    }

    @GetMapping("/fail")
    public String paymentFail(
            @RequestParam(required=false) String code,
            @RequestParam(required=false) String message,
            Model model
    ) {
        model.addAttribute("code", code);
        model.addAttribute("message", message);
        return "payment/fail";
    }
}
