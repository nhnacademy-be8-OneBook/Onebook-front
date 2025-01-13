package com.onebook.frontapi.service.grade;

import com.onebook.frontapi.dto.grade.GradeFeignResponse;
import com.onebook.frontapi.dto.grade.GradeResponse;
import com.onebook.frontapi.feign.grade.GradeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeClient gradeClient;

    // name 으로 등급 조회 (마이페이지 등급용)
    public List<GradeResponse> getGrades() {
        List<GradeResponse> result = new ArrayList<>();

        List<GradeFeignResponse> gradeFeignResponseList = gradeClient.getAllRequest();
        for(GradeFeignResponse grd : gradeFeignResponseList) {
            GradeResponse gvd = GradeResponse.from(grd);
            result.add(gvd);
        }

        return result;
    }
}
