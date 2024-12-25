package com.onebook.frontapi.point;

import com.onebook.frontapi.point.adapter.PointAdapter;
import com.onebook.frontapi.point.dto.CommonResponse;
import com.onebook.frontapi.point.dto.UserPointResponse;
import com.onebook.frontapi.point.enums.PointHistoryType;
import com.onebook.frontapi.point.exception.LoginRequiredException;
import com.onebook.frontapi.point.service.PointService;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // MockitoExtension을 사용하여 자동으로 Mock 객체 초기화
class PointServiceTest {

    @Mock  // Mock 객체로 선언
    private PointAdapter pointAdapter;

    @InjectMocks  // @Mock 객체가 자동으로 주입됨
    private PointService pointService;

    @BeforeEach
    void setUp() {
        // Mock setup is done automatically with @Mock and @InjectMocks
    }

    @Test
    void getUserPointHistories_shouldReturnPointHistories() {
        // Mock the response from PointAdapter
        List<UserPointResponse> mockPointHistories = Arrays.asList(
                new UserPointResponse(1L, 100, PointHistoryType.PURCHASE, null),
                new UserPointResponse(2L, 200, PointHistoryType.CASHBACK, null)
        );

        CommonResponse<List<UserPointResponse>> response = CommonResponse.<List<UserPointResponse>>builder()
                .header(null)  // Header not relevant for this test
                .result(mockPointHistories)
                .build();

        when(pointAdapter.getUserPointHistories()).thenReturn(response);

        List<UserPointResponse> result = pointService.getUserPointHistories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getPointHistoryValue());
        verify(pointAdapter, times(1)).getUserPointHistories();
    }

    @Test
    void getUserPointHistories_shouldThrowLoginRequiredException_whenUnauthorized() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getUserPointHistories()).thenThrow(new FeignException.Unauthorized("Unauthorized", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getUserPointHistories());
    }

    @Test
    void getUserPointHistories_shouldThrowLoginRequiredException_whenBadRequest() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getUserPointHistories()).thenThrow(new FeignException.BadRequest("Bad Request", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getUserPointHistories());
    }

    @Test
    void getUserPointHistories_shouldThrowLoginRequiredException_whenNotFound() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getUserPointHistories()).thenThrow(new FeignException.NotFound("Not Found", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getUserPointHistories());
    }
}
