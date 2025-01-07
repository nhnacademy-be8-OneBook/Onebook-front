package com.onebook.frontapi.dto.point.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointPolicyResponse {
    private String policyName;   // 포인트 정책 이름
    private int pointAmount;     // 포인트 양
    private String policyStatus; // 포인트 정책 상태 (예: 활성화, 비활성화 등)
}
