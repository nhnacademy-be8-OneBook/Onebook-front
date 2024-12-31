package com.onebook.frontapi.service.packaging;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.dto.packaging.PackagingRequestDTO;
import com.onebook.frontapi.feign.packaging.PackagingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingService {
    private final PackagingClient packagingClient;

    // create
    public void createPackaging(PackagingRequestDTO packagingRequestDTO) {

        packagingClient.createPackaging();
    }

    // read
    public List<PackagingResponseAdaptor> getAllPackaging() {
        return packagingClient.getAllPackaging();
    }
}