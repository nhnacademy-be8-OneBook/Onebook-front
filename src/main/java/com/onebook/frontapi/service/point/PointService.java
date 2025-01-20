package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.response.MemberPointResponse;
import com.onebook.frontapi.dto.point.response.PointLogResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PointService {
    List<MemberPointResponse> getMemberPointHistories(Long memberId);

    int getMemberPoint();
}
