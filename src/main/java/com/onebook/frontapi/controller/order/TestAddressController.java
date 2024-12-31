package com.onebook.frontapi.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// 삭제 필요
@Controller
public class TestAddressController {

    @GetMapping("/task/address-test")
    public String addressManagement(Model model) {
        // 테스트 데이터 (DB나 서비스에서 가져올 수 있음)
        List<Address> addressList = List.of(
                new Address("홍길동", "010-1234-5678", "서울특별시 강남구 테헤란로 123"),
                new Address("김철수", "010-8765-4321", "부산광역시 해운대구 우동 456")
        );
        model.addAttribute("addressList", addressList);
        return "order/addressManagement";
    }
}
