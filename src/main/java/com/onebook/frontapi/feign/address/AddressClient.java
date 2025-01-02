package com.onebook.frontapi.feign.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.AddMemberAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AddressClient",  url = "${onebook.gatewayUrl}")
public interface AddressClient {

    @PostMapping("/task/addresses")
    ResponseEntity<AddMemberAddressResponse> addMemberAddress
            (@RequestBody AddMemberAddressRequest addMemberAddressRequest);
//            (@RequestHeader("X-MEMBER-ID") Long memberId,@RequestBody AddMemberAddressRequest addMemberAddressRequest);

}
