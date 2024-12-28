package com.onebook.frontapi.service.packaging;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.feign.packaging.PackagingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingService {
    private final PackagingClient packagingClient;

    public List<PackagingResponseAdaptor> getAllPackaging() {

        List<PackagingResponseAdaptor> allPackaging = packagingClient.getAllPackaging();
        System.out.println("!!!!!! all packaging !!!!!" + allPackaging);

        return allPackaging;
    }
}
