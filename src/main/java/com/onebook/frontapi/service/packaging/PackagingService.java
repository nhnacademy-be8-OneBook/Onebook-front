package com.onebook.frontapi.service.packaging;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.dto.packaging.PackagingCreateDto;
import com.onebook.frontapi.dto.packaging.PackagingRequestDto;
import com.onebook.frontapi.feign.packaging.PackagingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingService {
    private final PackagingClient packagingClient;

    // create
    public void createPackaging(PackagingRequestDto packagingRequestDto) {
        PackagingCreateDto packagingCreateDto = new PackagingCreateDto(packagingRequestDto.getName(), packagingRequestDto.getPrice());

        packagingClient.createPackaging(packagingCreateDto);
    }

    // read
    public List<PackagingResponseAdaptor> getAllPackaging() {
        return packagingClient.getAllPackaging();
    }
}