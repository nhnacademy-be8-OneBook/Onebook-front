package com.onebook.frontapi.controller.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.AddMemberAddressResponse;
import com.onebook.frontapi.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록 페이지 불러오기
    @GetMapping("/address/register-form")
    public String getAddressRegisterForm(){
        return "/address/address-register-form";
    }

    // 배송지 목록 페이지 불러오기
    @GetMapping("/address/address-list")
    public String getAddressList(){

        return null;
    }

    // 배송지 등록 기능
    @PostMapping("/address/register")
    public String registerAddress(@ModelAttribute AddMemberAddressRequest addMemberAddressRequest){

        addressService.addMemberAddress(addMemberAddressRequest);
        return null;
    }

}
