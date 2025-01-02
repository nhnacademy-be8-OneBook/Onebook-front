package com.onebook.frontapi.service.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.AddMemberAddressResponse;
import com.onebook.frontapi.feign.address.AddressClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressClient addressClient;

    public AddMemberAddressResponse addMemberAddress(AddMemberAddressRequest addMemberAddressRequest){

        return addressClient.addMemberAddress(addMemberAddressRequest).getBody();
//        return addressClient.addMemberAddress(0L ,addMemberAddressRequest).getBody();
    }

}
