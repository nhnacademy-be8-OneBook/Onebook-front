package com.onebook.frontapi.point;

import com.onebook.frontapi.controller.my.point.PointController;
import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.service.point.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.onebook.frontapi.enums.PointHistoryType.PURCHASE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController pointController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pointController).build();
    }

    @Test
    public void testGetUserPointHistories_Success() throws Exception {
        MemberPointResponse response = MemberPointResponse.builder()
                .pointId(1L)
                .pointHistoryValue(100)
                .pointHistoryType(PURCHASE)
                .createdAt(LocalDateTime.now())
                .build();

        when(pointService.getMemberPointHistories()).thenReturn(Collections.singletonList(response));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "testUser");

        mockMvc.perform(get("/my/point/mypoints").session(session))  // 실제 매핑된 경로로 수정
                .andExpect(status().isOk())  // HTTP 200
                .andExpect(view().name("my/point/mypoints"))  // 뷰 이름 확인
                .andExpect(model().attribute("memberPointHistories", Collections.singletonList(response)));  // 모델에 데이터 확인
    }

    @Test
    public void testGetUserPointHistories_Fail_NoData() throws Exception {
        when(pointService.getMemberPointHistories()).thenReturn(Collections.emptyList());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "testUser");

        mockMvc.perform(get("/my/point/mypoints").session(session))  // 실제 매핑된 경로로 수정
                .andExpect(status().isOk())  // HTTP 200
                .andExpect(view().name("my/point/mypoints"))
                .andExpect(model().attribute("memberPointHistories", Collections.emptyList()));  // 빈 리스트 확인
    }
}
