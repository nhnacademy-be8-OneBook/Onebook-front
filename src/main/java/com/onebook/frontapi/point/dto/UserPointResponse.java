package com.onebook.frontapi.point.dto;

import java.time.LocalDateTime;

import com.onebook.frontapi.point.enums.PointHistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPointResponse {
    private Long pointId;
    private Integer pointHistoryValue;
    private PointHistoryType pointHistoryType;
    private LocalDateTime createdAt;
}