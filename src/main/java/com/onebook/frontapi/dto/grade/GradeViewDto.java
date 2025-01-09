package com.onebook.frontapi.dto.grade;

public record GradeViewDto(
        String name,
        int accumulationRate,
        String description
) {
    public static GradeViewDto from(GradeResponseDto gradeResponseDto) {
        return new GradeViewDto(
                gradeResponseDto.name(),
                gradeResponseDto.accumulationRate(),
                gradeResponseDto.description()
        );
    }
}
