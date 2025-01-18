package com.onebook.frontapi.dto.point.response;

import com.onebook.frontapi.enums.PointHistoryType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// memberId 에 해당하는 포인트 내역 반환 형식
@Getter
@Builder
@AllArgsConstructor
public class MemberPointResponse {
    private Long pointId;
    private Integer pointHistoryValue;
    private PointHistoryType pointHistoryType;
    private LocalDateTime createdAt;
}