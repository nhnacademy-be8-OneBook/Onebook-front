package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.response.MemberPointResponse;
import java.util.List;

public interface PointService {
    List<MemberPointResponse> getMemberPointHistories(Long memberId);
}
