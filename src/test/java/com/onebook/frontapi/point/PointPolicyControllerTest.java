package com.onebook.frontapi.point;

import com.onebook.frontapi.controller.point.PointPolicyController;
import com.onebook.frontapi.dto.point.request.CreatePointPolicyRequest;
import com.onebook.frontapi.dto.point.request.UpdatePointPolicyRequest;
import com.onebook.frontapi.feign.point.PointPolicyFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PointPolicyController.class)
public class PointPolicyControllerTest {

    @MockBean
    private PointPolicyFeignClient pointPolicyFeignClient;

    @InjectMocks
    private PointPolicyController pointPolicyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(pointPolicyController).build();
    }

    @Test
    public void testGetPointPolicies() throws Exception {
        // 포인트 정책 목록 페이지가 정상적으로 로드되는지 확인
        mockMvc.perform(get("/point/policies")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/allPointPolicies"))
                .andExpect(model().attributeExists("pointPolicies"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"));
    }

    @Test
    public void testCreatePointPolicyForm() throws Exception {
        // 포인트 정책 등록 폼 페이지가 정상적으로 로드되는지 확인
        mockMvc.perform(get("/point/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/createPointPolicy"))
                .andExpect(model().attributeExists("pointPolicyRequest"));
    }

    @Test
    public void testCreatePointPolicy() throws Exception {
        // 포인트 정책 등록 요청 시 유효성 검사 및 성공적인 리다이렉트 확인
        mockMvc.perform(post("/point/create")
                        .param("name", "Test Policy")
                        .param("description", "Test Description")
                        .param("points", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/point/policies"));
    }

    @Test
    public void testUpdatePointPolicyForm() throws Exception {
        // 포인트 정책 수정 폼 페이지가 정상적으로 로드되는지 확인
        mockMvc.perform(get("/point/update")
                        .param("policyId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/updatePointPolicy"))
                .andExpect(model().attributeExists("pointPolicyRequest"));
    }

    @Test
    public void testUpdatePointPolicy() throws Exception {
        // 포인트 정책 수정 요청 시 유효성 검사 및 성공적인 리다이렉트 확인
        mockMvc.perform(post("/point/update")
                        .param("policyId", "1")
                        .param("name", "Updated Policy")
                        .param("description", "Updated Description")
                        .param("points", "200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/point/policies"));
    }

    @Test
    public void testDeletePointPolicyConfirmation() throws Exception {
        // 포인트 정책 삭제 확인 페이지가 정상적으로 로드되는지 확인
        mockMvc.perform(get("/point/delete")
                        .param("policyId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/deletePointPolicy"))
                .andExpect(model().attributeExists("pointPolicy"));
    }

    @Test
    public void testDeletePointPolicy() throws Exception {
        // 포인트 정책 삭제 요청 시 성공적인 리다이렉트 확인
        mockMvc.perform(post("/point/delete")
                        .param("policyId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/point/policies"));
    }
}
