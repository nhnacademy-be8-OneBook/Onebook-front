package com.onebook.frontapi.dto.point.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePointPolicyRequest {
    private String policyName;   // 포인트 정책 이름
    private int pointAmount;     // 포인트 양
}
