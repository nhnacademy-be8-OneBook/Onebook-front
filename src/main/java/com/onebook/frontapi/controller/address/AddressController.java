package com.onebook.frontapi.controller.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.MemberAddressResponse;
import com.onebook.frontapi.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    // 배송지 등록 기능
    // 배송지 등록 페이지 불러오기
    @GetMapping("/address/register")
    public String getAddressRegisterForm(){

        return "/address/address-register-form";
    }
    // 배송지 등록하기
    @PostMapping("/address/register")
    public String registerAddress(@ModelAttribute AddMemberAddressRequest addMemberAddressRequest){

        addressService.addMemberAddress(addMemberAddressRequest);
        return "redirect:/address/all";
    }

    // 배송지 조회 기능
    // 나의 배송지 목록 페이지 불러오기
    @GetMapping("/address/all")
    public String getAddressList(Model model){

        List<MemberAddressResponse> memberAddresses = addressService.getAllMemberAddressByMemberId();
        model.addAttribute("memberAddresses",memberAddresses);

        return "/address/address-list";
    }
}
