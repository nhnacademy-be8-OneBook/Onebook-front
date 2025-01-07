package com.onebook.frontapi.feign.point;

import com.onebook.frontapi.dto.point.MemberPointResponse;
import org.springframework.cloud.openfeign.FeignClient;
import com.onebook.frontapi.common.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "pointFeignClient", url = "${onebook.gatewayUrl}")
public interface PointFeignClient {

    @GetMapping("/member/admin/point-policies")
    CommonResponse<List<MemberPointResponse>> getMemberPointHistories();
}