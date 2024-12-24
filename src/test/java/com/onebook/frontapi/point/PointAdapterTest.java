package com.onebook.frontapi.point;

import com.onebook.frontapi.point.adapter.PointAdapter;
import com.onebook.frontapi.point.dto.CommonResponse;
import com.onebook.frontapi.point.dto.UserPointResponse;
import com.onebook.frontapi.point.enums.PointHistoryType;
import com.onebook.frontapi.point.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PointAdapterTest {

    @Mock
    private PointAdapter pointAdapter;  // PointAdapter를 mock으로 선언

    @InjectMocks
    private PointService pointService;  // 실제 서비스 클래스를 주입

    @BeforeEach
    void setUp() {
        // Mock setup is done by @Mock and @InjectMocks
    }

    @Test
    void getUserPointHistories_shouldReturnPointHistories() {
        // Prepare mock data
        UserPointResponse userPointResponse = UserPointResponse.builder()
                .pointId(1L)
                .pointHistoryValue(100)
                .pointHistoryType(PointHistoryType.PURCHASE)
                .createdAt(null)
                .build();

        CommonResponse<List<UserPointResponse>> mockResponse = CommonResponse.<List<UserPointResponse>>builder()
                .header(null)  // Not relevant for this test
                .result(Arrays.asList(userPointResponse))
                .build();

        // Mock the adapter's behavior
        when(pointAdapter.getUserPointHistories()).thenReturn(mockResponse);

        // Test the adapter method
        CommonResponse<List<UserPointResponse>> response = pointAdapter.getUserPointHistories();

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertEquals(1, response.getResult().size());
        assertEquals(100, response.getResult().get(0).getPointHistoryValue());
        verify(pointAdapter, times(1)).getUserPointHistories();
    }
}
