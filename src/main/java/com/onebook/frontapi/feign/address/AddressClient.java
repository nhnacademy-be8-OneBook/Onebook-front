package com.onebook.frontapi.feign.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.request.DeleteMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.MemberAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "AddressClient",  url = "${onebook.gatewayUrl}")
public interface AddressClient {

    @PostMapping("/task/addresses")
    ResponseEntity<MemberAddressResponse> addMemberAddress
            (@RequestBody AddMemberAddressRequest addMemberAddressRequest);

    @GetMapping("/task/addresses")
    ResponseEntity<List<MemberAddressResponse>> getAllMemberAddressByMemberId();

    @DeleteMapping("/task/addresses")
    ResponseEntity<MemberAddressResponse> deleteMemberAddress
            (@RequestBody DeleteMemberAddressRequest deleteMemberAddressRequest);


}
