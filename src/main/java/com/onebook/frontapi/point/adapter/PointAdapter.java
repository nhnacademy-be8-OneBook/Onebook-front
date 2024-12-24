package com.onebook.frontapi.point.adapter;

import java.util.List;

import com.onebook.frontapi.point.dto.CommonResponse;
import com.onebook.frontapi.point.dto.UserPointResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "pointAdapter", url = "${onebook.gatewayUrl}/api/bookstore/v1/points")
public interface PointAdapter {

    @GetMapping
    CommonResponse<List<UserPointResponse>> getUserPointHistories();
}
