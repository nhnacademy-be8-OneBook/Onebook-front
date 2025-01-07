package com.onebook.frontapi.controller.point;

import com.onebook.frontapi.dto.point.request.CreatePointPolicyRequest;
import com.onebook.frontapi.dto.point.response.PointPolicyResponse;
import com.onebook.frontapi.service.point.PointPolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/admin")
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    public PointPolicyController(PointPolicyService pointPolicyService) {
        this.pointPolicyService = pointPolicyService;
    }

    // 포인트 정책 생성
    @PostMapping("/point-policies")
    public ResponseEntity<PointPolicyResponse> createPointPolicy(@RequestBody CreatePointPolicyRequest policyRequest) {
        PointPolicyResponse response = pointPolicyService.createPointPolicy(policyRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 특정 포인트 정책 조회
    @GetMapping("/point-policies/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> findPointPolicyById(@PathVariable String pointPolicyId) {
        PointPolicyResponse response = pointPolicyService.findPointPolicyById(pointPolicyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}