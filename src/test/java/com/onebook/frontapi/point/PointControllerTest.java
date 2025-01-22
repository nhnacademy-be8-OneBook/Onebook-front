package com.onebook.frontapi.point;

import com.onebook.frontapi.controller.point.PointController;
import com.onebook.frontapi.dto.point.response.MemberPointResponse;
import com.onebook.frontapi.service.point.PointService;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController pointController;

    private HttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
    }

    // 1. 로그인된 사용자: 포인트 히스토리가 정상적으로 반환되는지 확인
    @Test
    public void testGetUserPointHistories_Success() throws Exception {
        // 세션에 userId와 user 정보를 설정
        session.setAttribute("user", new Object());
        session.setAttribute("userId", 1L);

        // 서비스에서 반환할 포인트 히스토리 데이터 설정
        MemberPointResponse pointHistory1 = new MemberPointResponse(1L, 100, "포인트 적립");
        MemberPointResponse pointHistory2 = new MemberPointResponse(2L, 200, "포인트 사용");
        when(pointService.getMemberPointHistories(1L)).thenReturn(Arrays.asList(pointHistory1, pointHistory2));

        // 요청을 보내고 결과를 검증
        mockMvc.perform(get("/point/myPoint").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("mypage/mypagePoint"))
                .andExpect(model().attributeExists("memberPointHistories"))
                .andExpect(model().attribute("memberPointHistories", Arrays.asList(pointHistory1, pointHistory2)));
    }

    // 2. 로그인되지 않은 사용자: 로그인 페이지로 리다이렉트되는지 확인
    @Test
    public void testGetUserPointHistories_NotLoggedIn() throws Exception {
        // 세션에 user와 userId가 없는 상태로 요청
        mockMvc.perform(get("/point/myPoint").session((MockHttpSession) session))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login"));
    }

    // 3. FeignException.Unauthorized 예외: 401 상태 코드와 함께 로그인 페이지로 리다이렉트
    @Test
    public void testGetUserPointHistories_Unauthorized() throws Exception {
        // 세션에 userId와 user 정보를 설정
        session.setAttribute("user", new Object());
        session.setAttribute("userId", 1L);

        // Unauthorized 예외를 발생시키기 위해 FeignException.Unauthorized 예외 던지기
        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
                "Unauthorized",
                null,
                new byte[0],
                null
        );

        when(pointService.getMemberPointHistories(1L)).thenThrow(unauthorizedException);

        // 요청을 보내고 결과를 검증
        mockMvc.perform(get("/point/myPoint").session((MockHttpSession) session))
                .andExpect(status().isUnauthorized())  // 401 Unauthorized 상태 코드 검증
                .andExpect(view().name("redirect:/login"));
    }

    // 4. FeignException.InternalServerError 예외: 500 상태 코드와 "서버 오류" 메시지
    @Test
    public void testGetUserPointHistories_InternalServerError() throws Exception {
        // 세션에 userId와 user 정보를 설정
        session.setAttribute("user", new Object());
        session.setAttribute("userId", 1L);

        // InternalServerError 예외를 발생시키기 위해 FeignException.InternalServerError 예외 던지기
        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(
                "Internal server error",
                null,
                new byte[0],
                null
        );

        when(pointService.getMemberPointHistories(1L)).thenThrow(internalServerError);

        // 요청을 보내고 결과를 검증
        mockMvc.perform(get("/point/myPoint").session((MockHttpSession) session))
                .andExpect(status().isInternalServerError())  // 500 상태 코드 검증
                .andExpect(status().reason("서버 오류"));
    }

    // 5. RuntimeException 예외: 500 상태 코드와 "서버 오류" 메시지
    @Test
    public void testGetUserPointHistories_RuntimeException() throws Exception {
        // 세션에 userId와 user 정보를 설정
        session.setAttribute("user", new Object());
        session.setAttribute("userId", 1L);

        // RuntimeException 예외를 발생시키기 위해 런타임 예외 던지기
        when(pointService.getMemberPointHistories(1L)).thenThrow(new RuntimeException("Runtime exception"));

        // 요청을 보내고 결과를 검증
        mockMvc.perform(get("/point/myPoint").session((MockHttpSession) session))
                .andExpect(status().isInternalServerError())  // 500 상태 코드 검증
                .andExpect(status().reason("서버 오류"));
    }
}
