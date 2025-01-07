package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.request.CreatePointPolicyRequest;
import com.onebook.frontapi.dto.point.response.PointPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pointPolicyService", url = "http://localhost:8500/task/member/admin")
public interface PointPolicyService {

    @PostMapping("/point-policies")
    PointPolicyResponse createPointPolicy(@RequestBody CreatePointPolicyRequest pointPolicyRequest);

    @GetMapping("/point-policies/{pointPolicyId}")
    PointPolicyResponse findPointPolicyById(@PathVariable("pointPolicyId") String pointPolicyId);

    @GetMapping("/point-policies")
    Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable);
}
