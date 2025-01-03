package com.onebook.frontapi.service.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.AddMemberAddressResponse;
import com.onebook.frontapi.dto.address.response.GetMemberAddressResponse;
import com.onebook.frontapi.feign.address.AddressClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressClient addressClient;

    public AddMemberAddressResponse addMemberAddress(AddMemberAddressRequest addMemberAddressRequest) {
        return addressClient.addMemberAddress(addMemberAddressRequest).getBody();
    }

//    public List<MemberAddressResponse> getAllMemberAddressByMemberId() {
//        return addressClient
//    }
}
