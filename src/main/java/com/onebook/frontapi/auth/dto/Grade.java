package com.onebook.frontapi.auth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Grade {


    private Integer id; // default(REGULAR) ID: 1

    private String name;

    private int accumulationRate;

    private String description;


}
