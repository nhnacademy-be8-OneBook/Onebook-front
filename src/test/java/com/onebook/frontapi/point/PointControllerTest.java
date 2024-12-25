package com.onebook.frontapi.point;

import com.onebook.frontapi.point.controller.PointController;
import com.onebook.frontapi.point.dto.UserPointResponse;
import com.onebook.frontapi.point.enums.PointHistoryType;
import com.onebook.frontapi.point.service.PointService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PointController.class)
class PointControllerTest {

    @MockBean
    private PointService pointService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PointController(pointService)).build();
    }

    @Test
    void getUserPointHistories_shouldReturnPointHistories() throws Exception {
        List<UserPointResponse> mockPointHistories = Arrays.asList(
                new UserPointResponse(1L, 100, PointHistoryType.PURCHASE, null),
                new UserPointResponse(2L, 200, PointHistoryType.CASHBACK, null)
        );

        when(pointService.getUserPointHistories()).thenReturn(mockPointHistories);

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isOk())
                .andExpect(view().name("point/point_history"))
                .andExpect(model().attributeExists("userPointHistories"))
                .andExpect(model().attribute("userPointHistories", mockPointHistories));

        verify(pointService, times(1)).getUserPointHistories();
    }

    @Test
    void getUserPointHistories_shouldReturnError_whenServiceFails() throws Exception {
        when(pointService.getUserPointHistories()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "An error occurred while fetching point histories"));

        verify(pointService, times(1)).getUserPointHistories();
    }

    @Test
    void getUserPointHistories_shouldReturnEmptyList_whenNoHistoriesFound() throws Exception {
        when(pointService.getUserPointHistories()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isOk())
                .andExpect(view().name("point/point_history"))
                .andExpect(model().attributeExists("userPointHistories"))
                .andExpect(model().attribute("userPointHistories", Arrays.asList()));

        verify(pointService, times(1)).getUserPointHistories();
    }

    @Test
    void getUserPointHistories_shouldHandleServiceException() throws Exception {
        when(pointService.getUserPointHistories()).thenThrow(new ServiceException("An error occurred while fetching point histories"));

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "An error occurred while fetching point histories"));  // 기대값 수정

        verify(pointService, times(1)).getUserPointHistories();
    }
}
