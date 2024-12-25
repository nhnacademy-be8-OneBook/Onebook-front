package com.onebook.frontapi.point;

import com.onebook.frontapi.point.adapter.PointAdapter;
import com.onebook.frontapi.point.dto.CommonResponse;
import com.onebook.frontapi.point.dto.MemberPointResponse;
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
    void getMemberPointHistories_shouldReturnPointHistories() {
        // Mock the response from PointAdapter
        List<MemberPointResponse> mockPointHistories = Arrays.asList(
                new MemberPointResponse(1L, 100, PointHistoryType.PURCHASE, null),
                new MemberPointResponse(2L, 200, PointHistoryType.CASHBACK, null)
        );

        CommonResponse<List<MemberPointResponse>> response = CommonResponse.<List<MemberPointResponse>>builder()
                .header(null)  // Header not relevant for this test
                .result(mockPointHistories)
                .build();

        when(pointAdapter.getMemberPointHistories()).thenReturn(response);

        List<MemberPointResponse> result = pointService.getMemberPointHistories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getPointHistoryValue());
        verify(pointAdapter, times(1)).getMemberPointHistories();
    }

    @Test
    void getMemberPointHistories_shouldThrowLoginRequiredException_whenUnauthorized() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getMemberPointHistories()).thenThrow(new FeignException.Unauthorized("Unauthorized", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getMemberPointHistories());
    }

    @Test
    void getMemberPointHistories_shouldThrowLoginRequiredException_whenBadRequest() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getMemberPointHistories()).thenThrow(new FeignException.BadRequest("Bad Request", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getMemberPointHistories());
    }

    @Test
    void getMemberPointHistories_shouldThrowLoginRequiredException_whenNotFound() {
        // Create a mock request and byte array (for proper constructor usage)
        Request request = mock(Request.class);
        byte[] body = new byte[0];

        // Throw the FeignException with the necessary arguments
        when(pointAdapter.getMemberPointHistories()).thenThrow(new FeignException.NotFound("Not Found", request, body, null));

        assertThrows(LoginRequiredException.class, () -> pointService.getMemberPointHistories());
    }
}