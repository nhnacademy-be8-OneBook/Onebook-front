package com.onebook.frontapi.point;

import com.onebook.frontapi.controller.point.PointController;
import com.onebook.frontapi.enums.PointHistoryType;
import com.onebook.frontapi.service.point.PointService;
import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.common.CommonResponse;
import com.onebook.frontapi.common.CommonHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 전체 애플리케이션 컨텍스트를 로드
class PointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController pointController;

    @BeforeEach
    void setUp() {
        // MockMvc 빌더를 설정하여 테스트 환경을 준비합니다.
        mockMvc = MockMvcBuilders.standaloneSetup(pointController).build();
    }

    @Test
    void testGetUserPointHistories() throws Exception {
        // Mock 데이터 준비
        MemberPointResponse pointHistory1 = MemberPointResponse.builder()
                .pointId(1L)
                .pointHistoryValue(100)
                .pointHistoryType(PointHistoryType.valueOf("PURCHASE"))
                .createdAt(LocalDateTime.now())
                .build();

        MemberPointResponse pointHistory2 = MemberPointResponse.builder()
                .pointId(2L)
                .pointHistoryValue(50)
                .pointHistoryType(PointHistoryType.valueOf("CASHBACK"))
                .createdAt(LocalDateTime.now())
                .build();

        List<MemberPointResponse> mockResponse = Arrays.asList(pointHistory1, pointHistory2);
        CommonResponse<List<MemberPointResponse>> commonResponse = CommonResponse.<List<MemberPointResponse>>builder()
                .header(CommonHeader.builder().httpStatus(org.springframework.http.HttpStatus.OK).resultMessage("Success").build())
                .result(mockResponse)
                .build();

        // 서비스 메서드 모킹
        when(pointService.getMemberPointHistories()).thenReturn(mockResponse);

        // GET 요청 보내고 결과 검증
        mockMvc.perform(get("/point/myPoint"))
                .andExpect(status().isOk())  // 상태 코드 200 OK 확인
                .andExpect(view().name("mypage/mypagePoint"))  // 뷰 이름 확인
                .andExpect(model().attributeExists("userPointHistories"))  // 모델에 "userPointHistories" 속성 존재 확인
                .andExpect(model().attribute("userPointHistories", mockResponse));  // 모델 속성 값 검증
    }
}
