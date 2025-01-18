package com.onebook.frontapi.controller.my.home;

import com.onebook.frontapi.dto.member.MemberResponse;
import com.onebook.frontapi.dto.myhome.MyOrderStatusResponse;
import com.onebook.frontapi.dto.order.OrderResponse;
import com.onebook.frontapi.service.member.MemberService;
import com.onebook.frontapi.service.myhome.MyHomeService;
import com.onebook.frontapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MyHomeController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final MyHomeService myHomeService;

    @GetMapping("/home")
    public String myHome(Pageable pageable, Model model) {
        MemberResponse memberResponse = memberService.getMember();
        Page<OrderResponse> orderResponses = orderService.getOrders(null, pageable);
        MyOrderStatusResponse myOrderStatusResponse = myHomeService.getMyOrderStatus(orderResponses);

        model.addAttribute("member", memberResponse);
        model.addAttribute("myOrderStatus", myOrderStatusResponse);
        return "my/home/home";
    }

}
