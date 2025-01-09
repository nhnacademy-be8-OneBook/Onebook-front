package com.onebook.frontapi.service.grade;

import com.onebook.frontapi.dto.grade.GradeResponseDto;
import com.onebook.frontapi.dto.grade.GradeViewDto;
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
    public List<GradeViewDto> getGrades() {
        List<GradeViewDto> result = new ArrayList<>();

        List<GradeResponseDto> gradeResponseDtoList = gradeClient.getAllRequest();
        for(GradeResponseDto grd : gradeResponseDtoList) {
            GradeViewDto gvd = GradeViewDto.from(grd);
            result.add(gvd);
        }

        return result;
    }
}
