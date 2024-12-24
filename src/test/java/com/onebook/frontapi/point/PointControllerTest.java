package com.onebook.frontapi.point;

import com.onebook.frontapi.point.controller.PointController;
import com.onebook.frontapi.point.dto.UserPointResponse;
import com.onebook.frontapi.point.enums.PointHistoryType;
import com.onebook.frontapi.point.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController pointController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pointController).build();
    }

    @Test
    void getUserPointHistories_shouldReturnPointHistories() throws Exception {
        // Mocking the service call to return a list of UserPointResponse objects
        List<UserPointResponse> mockPointHistories = Arrays.asList(
                new UserPointResponse(1L, 100, PointHistoryType.PURCHASE, null),
                new UserPointResponse(2L, 200, PointHistoryType.CASHBACK, null)
        );

        when(pointService.getUserPointHistories()).thenReturn(mockPointHistories);

        // Perform the GET request to the controller and validate the response
        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isOk()) // Ensure HTTP 200 OK response
                .andExpect(view().name("point/point_history")) // Check view name
                .andExpect(model().attributeExists("userPointHistories")) // Check model attribute
                .andExpect(model().attribute("userPointHistories", mockPointHistories)); // Check the model attribute value

        // Verify that the service method was called exactly once
        verify(pointService, times(1)).getUserPointHistories();
    }

    @Test
    void getUserPointHistories_shouldReturnError_whenServiceFails() throws Exception {
        // Simulate an exception in the service layer (e.g., LoginRequiredException)
        when(pointService.getUserPointHistories()).thenThrow(new RuntimeException("Error"));

        // Perform the GET request and validate that the error page is returned
        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isInternalServerError()); // Check for internal server error

        // Verify that the service method was called exactly once
        verify(pointService, times(1)).getUserPointHistories();
    }
}