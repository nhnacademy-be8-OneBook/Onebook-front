package com.onebook.frontapi.feign.grade;

import com.onebook.frontapi.dto.grade.GradeFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "gradeClient", url="${onebook.gatewayUrl}")
public interface GradeClient {
    @GetMapping("/task/grades/{name}")
    GradeFeignResponse getRequest(@PathVariable(name = "name") String name);

    @GetMapping("/task/grades/list")
    List<GradeFeignResponse> getAllRequest();
}
