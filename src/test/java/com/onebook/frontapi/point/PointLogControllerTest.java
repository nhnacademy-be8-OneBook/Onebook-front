package com.onebook.frontapi.point;

import com.onebook.frontapi.controller.point.PointLogController;
import com.onebook.frontapi.dto.point.response.PointLogPageResponseDto;
import com.onebook.frontapi.feign.point.PointFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PointLogControllerTest {

    @InjectMocks
    private PointLogController pointLogController;

    @Mock
    private PointFeignClient pointFeignClient;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPointLogs_Success() {
        // Given
        PointLogPageResponseDto logs = new PointLogPageResponseDto();
        when(pointFeignClient.getPointLogs(0, 15)).thenReturn(logs);

        // When
        String viewName = pointLogController.getPointLogs(0, 15, model);

        // Then
        assertEquals("my/point/mypoint", viewName);
        verify(model).addAttribute("pointLogs", logs);
    }

    @Test
    void testGetPointLogs_ParamsPassedCorrectly() {
        // Given: PointFeignClient의 getPointLogs 호출 시 예외가 없도록 모킹
        PointLogPageResponseDto logs = new PointLogPageResponseDto();  // 응답 객체 준비
        when(pointFeignClient.getPointLogs(0, 15)).thenReturn(logs);

        // When: 컨트롤러 메서드 호출
        pointLogController.getPointLogs(0, 15, model);

        // Then: getPointLogs가 올바른 파라미터로 호출되었는지 검증
        verify(pointFeignClient).getPointLogs(0, 15);  // getPointLogs가 (0, 15)로 호출되었는지 확인
    }

    @Test
    void testGetPointLogs_EmptyResponse() {
        // Given
        PointLogPageResponseDto emptyLogs = new PointLogPageResponseDto();  // 빈 응답
        when(pointFeignClient.getPointLogs(0, 15)).thenReturn(emptyLogs);

        // When
        String viewName = pointLogController.getPointLogs(0, 15, model);

        // Then
        assertEquals("my/point/mypoint", viewName);
        verify(model).addAttribute("pointLogs", emptyLogs);
    }

    @Test
    void testGetPointLogs_AnotherPageSize() {
        // Given
        PointLogPageResponseDto logs = new PointLogPageResponseDto();
        when(pointFeignClient.getPointLogs(1, 10)).thenReturn(logs);

        // When
        String viewName = pointLogController.getPointLogs(1, 10, model);

        // Then
        assertEquals("my/point/mypoint", viewName);
        verify(model).addAttribute("pointLogs", logs);
    }

    @Test
    void testGetPointLogs_InvalidPageNumber() {
        // Given
        PointLogPageResponseDto logs = new PointLogPageResponseDto();
        when(pointFeignClient.getPointLogs(-1, 15)).thenReturn(logs);

        // When
        String viewName = pointLogController.getPointLogs(-1, 15, model);

        // Then
        assertEquals("my/point/mypoint", viewName); // 기본 페이지가 0으로 처리될 수 있다고 가정
        verify(model).addAttribute("pointLogs", logs);
    }

    @Test
    void testModelAttribute_CorrectAdd() {
        // Given: PointFeignClient에서 정상적으로 데이터를 반환하도록 모킹
        PointLogPageResponseDto logs = new PointLogPageResponseDto();
        when(pointFeignClient.getPointLogs(0, 15)).thenReturn(logs);

        // When: 컨트롤러 메서드 호출
        pointLogController.getPointLogs(0, 15, model);

        // Then: model에 올바르게 attribute가 추가되었는지 확인
        verify(model).addAttribute("pointLogs", logs);  // pointLogs에 logs가 추가되었는지 확인
    }
}
