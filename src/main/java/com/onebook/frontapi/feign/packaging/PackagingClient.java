package com.onebook.frontapi.feign.packaging;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//@FeignClient(name = "task-api", url = "http://localhost:8510")
@FeignClient(name = "${onebook.taskName}", url = "${onebook.gatewayUrl}")
@Component
public interface PackagingClient {
    @GetMapping("/task/packagings")
    List<PackagingResponseAdaptor> getAllPackaging();

    @PostMapping("/task/admin/packaging")
    void createPackaging();
}