//실제 환경이라고 가정하고 한 테스트
package com.onebook.frontapi.point;

import com.onebook.frontapi.common.CommonResponse;
import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.enums.PointHistoryType;
import com.onebook.frontapi.feign.point.PointFeignClient;
import com.onebook.frontapi.service.point.PointService;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest
public class FeignAndFrontConnectionTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointFeignClient pointFeignClient;

    @Mock
    private Request mockRequest;  // Feign Request 객체 mock

    private static final String TEST_URL = "http://localhost:8210"; // FeignClient의 실제 URL(gatewayUrl)

    @BeforeEach
    public void setUp() {
        // 각 테스트가 실행되기 전에 호출되는 초기화 메서드
    }

    // Configuration class to add RequestContextListener
    @Configuration
    static class TestConfig {
        @Bean
        public RequestContextListener requestContextListener() {
            return new RequestContextListener();
        }
    }

    @Test
    public void testGetMemberPointHistories_Success() {
        // 정상적인 FeignClient 응답을 mock
        MemberPointResponse mockResponse1 = MemberPointResponse.builder()
                .pointId(1L)
                .pointHistoryValue(100)
                .pointHistoryType(PointHistoryType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .build();

        MemberPointResponse mockResponse2 = MemberPointResponse.builder()
                .pointId(2L)
                .pointHistoryValue(200)
                .pointHistoryType(PointHistoryType.CASHBACK)
                .createdAt(LocalDateTime.now())
                .build();

        List<MemberPointResponse> mockPointHistories = Arrays.asList(mockResponse1, mockResponse2);

        // FeignClient가 정상적으로 반환하는 값을 mock
        given(pointFeignClient.getUserPointHistories())
                .willReturn(new CommonResponse<>(null, mockPointHistories));

        // PointService 메서드 호출
        List<MemberPointResponse> result = pointService.getMemberPointHistories();

        // 결과 검증
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getPointHistoryValue()).isEqualTo(100);
        assertThat(result.get(1).getPointHistoryType()).isEqualTo(PointHistoryType.CASHBACK);
    }

    @Test
    public void testGetMemberPointHistories_Unauthorized() {
        // Unauthorized 예외 발생
        byte[] responseBody = "Unauthorized".getBytes(StandardCharsets.UTF_8);

        // Request 객체를 mock하여 전달
        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);

        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
                "Unauthorized",
                mockRequest,
                responseBody,
                Map.of()
        );

        // Unauthorized 예외를 mock
        given(pointFeignClient.getUserPointHistories())
                .willThrow(unauthorizedException);

        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            pointService.getMemberPointHistories();
        });

        assertThat(exception.getStatusCode()).isEqualTo(UNAUTHORIZED);
        assertThat(exception.getReason()).isEqualTo("로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.");
    }

    @Test
    public void testGetMemberPointHistories_BadRequest() {
        // Bad Request 예외 발생
        byte[] responseBody = "Bad Request".getBytes(StandardCharsets.UTF_8);

        // Request 객체를 mock하여 전달
        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);

        FeignException.BadRequest badRequestException = new FeignException.BadRequest(
                "Bad Request",
                mockRequest,
                responseBody,
                Map.of()
        );

        // BadRequest 예외를 mock
        given(pointFeignClient.getUserPointHistories())
                .willThrow(badRequestException);

        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            pointService.getMemberPointHistories();
        });

        assertThat(exception.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("잘못된 요청입니다.");
    }

    @Test
    public void testGetMemberPointHistories_InternalServerError() {
        // Internal Server Error 예외 발생
        byte[] responseBody = "Internal Server Error".getBytes(StandardCharsets.UTF_8);

        // Request 객체를 mock하여 전달
        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);

        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(
                "Internal Server Error",
                mockRequest,
                responseBody,
                Map.of()
        );

        // Internal Server Error 예외를 mock
        given(pointFeignClient.getUserPointHistories())
                .willThrow(internalServerError);

        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            pointService.getMemberPointHistories();
        });

        assertThat(exception.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(exception.getReason()).isEqualTo("서버 오류");
    }

    @Test
    public void testGetMemberPointHistories_IllegalStateException() {
        // IllegalStateException을 강제로 발생시키는 경우
        given(pointFeignClient.getUserPointHistories())
                .willThrow(new IllegalStateException("잘못된 상태"));

        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            pointService.getMemberPointHistories();
        });

        assertThat(exception.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(exception.getReason()).isEqualTo("알 수 없는 오류가 발생했습니다.");
    }
}


//package com.onebook.frontapi.point;
//
//import com.onebook.frontapi.common.CommonResponse;
//import com.onebook.frontapi.dto.point.MemberPointResponse;
//import com.onebook.frontapi.enums.PointHistoryType;
//import com.onebook.frontapi.feign.point.PointFeignClient;
//import com.onebook.frontapi.service.point.PointService;
//import feign.FeignException;
//import feign.Request;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.BDDMockito.given;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//public class FeignAndFrontConnectionTest {
//
//    @InjectMocks
//    private PointService pointService;
//
//    @Mock
//    private PointFeignClient pointFeignClient;
//
//    @Mock
//    private Request mockRequest;  // Feign Request 객체 mock
//
//    private static final String TEST_URL = "http://localhost:8210"; // FeignClient의 실제 URL
//
//    @BeforeEach
//    public void setUp() {
//        // 각 테스트가 실행되기 전에 호출되는 초기화 메서드
//    }
//
//    @Test
//    public void testGetMemberPointHistories_Success() {
//        // 정상적인 FeignClient 응답을 mock
//        MemberPointResponse mockResponse1 = MemberPointResponse.builder()
//                .pointId(1L)
//                .pointHistoryValue(100)
//                .pointHistoryType(PointHistoryType.PURCHASE)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        MemberPointResponse mockResponse2 = MemberPointResponse.builder()
//                .pointId(2L)
//                .pointHistoryValue(200)
//                .pointHistoryType(PointHistoryType.CASHBACK)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        List<MemberPointResponse> mockPointHistories = Arrays.asList(mockResponse1, mockResponse2);
//
//        // FeignClient가 정상적으로 반환하는 값을 mock
//        given(pointFeignClient.getUserPointHistories())
//                .willReturn(new CommonResponse<>(null, mockPointHistories));
//
//        // PointService 메서드 호출
//        List<MemberPointResponse> result = pointService.getMemberPointHistories();
//
//        // 결과 검증
//        assertThat(result).isNotEmpty();
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).getPointHistoryValue()).isEqualTo(100);
//        assertThat(result.get(1).getPointHistoryType()).isEqualTo(PointHistoryType.CASHBACK);
//    }
//
//    @Test
//    public void testGetMemberPointHistories_Unauthorized() {
//        // Unauthorized 예외 발생
//        byte[] responseBody = "Unauthorized".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
//                "Unauthorized",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // Unauthorized 예외를 mock
//        given(pointFeignClient.getUserPointHistories())
//                .willThrow(unauthorizedException);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode().value()).isEqualTo(401);
//        assertThat(exception.getReason()).isEqualTo("로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.");
//    }
//
//    @Test
//    public void testGetMemberPointHistories_BadRequest() {
//        // Bad Request 예외 발생
//        byte[] responseBody = "Bad Request".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.BadRequest badRequestException = new FeignException.BadRequest(
//                "Bad Request",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // BadRequest 예외를 mock
//        given(pointFeignClient.getUserPointHistories())
//                .willThrow(badRequestException);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode().value()).isEqualTo(400);
//        assertThat(exception.getReason()).isEqualTo("잘못된 요청입니다.");
//    }
//
//    @Test
//    public void testGetMemberPointHistories_InternalServerError() {
//        // Internal Server Error 예외 발생
//        byte[] responseBody = "Internal Server Error".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(
//                "Internal Server Error",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // Internal Server Error 예외를 mock
//        given(pointFeignClient.getUserPointHistories())
//                .willThrow(internalServerError);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode().value()).isEqualTo(500);
//        assertThat(exception.getReason()).isEqualTo("서버 오류");
//    }
//}