package com.onebook.frontapi.point;

import com.onebook.frontapi.point.controller.PointController;
import com.onebook.frontapi.point.dto.MemberPointResponse;
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
    void getMemberPointHistories_shouldReturnPointHistories() throws Exception {
        List<MemberPointResponse> mockPointHistories = Arrays.asList(
                new MemberPointResponse(1L, 100, PointHistoryType.PURCHASE, null),
                new MemberPointResponse(2L, 200, PointHistoryType.CASHBACK, null)
        );

        when(pointService.getMemberPointHistories()).thenReturn(mockPointHistories);

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isOk())
                .andExpect(view().name("point/point_history"))
                .andExpect(model().attributeExists("memberPointHistories"))
                .andExpect(model().attribute("memberPointHistories", mockPointHistories));

        verify(pointService, times(1)).getMemberPointHistories();
    }

    @Test
    void getMemberPointHistories_shouldReturnError_whenServiceFails() throws Exception {
        when(pointService.getMemberPointHistories()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "An error occurred while fetching point histories"));

        verify(pointService, times(1)).getMemberPointHistories();
    }

    @Test
    void getMemberPointHistories_shouldReturnEmptyList_whenNoHistoriesFound() throws Exception {
        when(pointService.getMemberPointHistories()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isOk())
                .andExpect(view().name("point/point_history"))
                .andExpect(model().attributeExists("memberPointHistories"))
                .andExpect(model().attribute("memberPointHistories", Arrays.asList()));

        verify(pointService, times(1)).getMemberPointHistories();
    }

    @Test
    void getMemberPointHistories_shouldHandleServiceException() throws Exception {
        when(pointService.getMemberPointHistories()).thenThrow(new ServiceException("An error occurred while fetching point histories"));

        mockMvc.perform(get("/point/my_point"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "An error occurred while fetching point histories"));

        verify(pointService, times(1)).getMemberPointHistories();
    }
}
