package com.onebook.frontapi.controller.order;

import com.onebook.frontapi.dto.delivery.DeliveryRequestDto;
import com.onebook.frontapi.dto.order.OrderAddressResponseDto;
import com.onebook.frontapi.dto.order.OrderByStatusResponseDto;
import com.onebook.frontapi.dto.order.OrderRegisterResponseDto;
import com.onebook.frontapi.dto.order.OrderRequestDto;
import com.onebook.frontapi.service.member.MemberService;
import com.onebook.frontapi.service.order.OrderAddressService;
import com.onebook.frontapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final OrderAddressService orderAddressService;

//    /*
    @GetMapping("/order/register")
    public String orderRegister(Model model) {
        // 사용자의 전화번호
        String orderderPhoneNumber = memberService.getMember().phoneNumber();
        model.addAttribute("ordererPhoneNumber", orderderPhoneNumber);

        List<OrderAddressResponseDto> allOrderAddress = orderAddressService.getAllOrderAddress();
        model.addAttribute("allOrderAddress", allOrderAddress);

        // 배송 선택 날짜
        // TODO utils에 넘기고싶음
        List<Map<String, String>> reservationDates = new ArrayList<>(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        for (int i = 1; i < 4; i++) {
            LocalDate day = LocalDate.now().plusDays(i+1);

            reservationDates.add(Map.of(
                    "orderNum", String.valueOf(i),
                    "completedDate", String.valueOf(day),
                    "description", String.valueOf(day.format(formatter))
            ));
        }
        model.addAttribute("reservationDates", reservationDates);

        if (allOrderAddress.get(0).isDefaultLocation() == true) {
            model.addAttribute("orderAddressDefaultLocation", allOrderAddress.get(0));
        }

        return "order/register";
    }
//    */

    @PostMapping("/order/register")
    public String submitOrder(@ModelAttribute OrderRegisterResponseDto orderRegisterResponseDto,  @ModelAttribute DeliveryRequestDto deliveryRequestDto, RedirectAttributes redirectAttributes) {
        // 받은 객체로 로직 수행
        System.out.println("포장지: " + orderRegisterResponseDto.getPackagingName() + " (" + orderRegisterResponseDto.getPackagingPrice() + ")");
        System.out.println("주문인: " + orderRegisterResponseDto.getOrdererName() + ", " + orderRegisterResponseDto.getOrdererPhone());
//        System.out.println("수령인: " + orderRegisterResponseDto.getRecipientName() + ", " + orderRegisterResponseDto.getReceiverPhone() + ", " + orderRegisterResponseDto.getReceiverAddress());

        // 데이터를 리다이렉트할 페이지로 전달
        redirectAttributes.addFlashAttribute("orderSuccess", true);
        redirectAttributes.addFlashAttribute("orderDetails", orderRegisterResponseDto);

        orderService.createOrder(orderRegisterResponseDto);
        System.out.println(deliveryRequestDto);

        return "redirect:/order/success"; // 등록 성공 페이지로 이동
    }

    @GetMapping("/order/success")
    public String showOrderSuccess(Model model) {
        if (model.containsAttribute("orderSuccess")) {
//             성공 메시지나 주문 정보 처리
            OrderRegisterResponseDto orderDetails = (OrderRegisterResponseDto) model.getAttribute("orderDetails");
            System.out.println("주문 성공! " + orderDetails);
        }
        return "order/orderSuccess"; // 주문 성공 페이지 반환
    }

    @GetMapping("/admin/orders")
    public String orderStatus(Model model, @RequestParam String status) {
        List<OrderByStatusResponseDto> ordersByStatus = orderService.getOrdersByStatus(status);
        model.addAttribute("ordersByStatus", ordersByStatus);
        return "admin/orderStatus";
    }
}
