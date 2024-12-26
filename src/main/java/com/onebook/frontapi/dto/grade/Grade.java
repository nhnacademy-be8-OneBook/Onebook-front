package com.onebook.frontapi.dto.grade;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record Grade(
        Integer id, // default(REGULAR) ID: 1
        String name,
        int accumulationRate,
        String description

) { }
