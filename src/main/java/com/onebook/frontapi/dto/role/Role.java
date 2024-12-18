package com.onebook.frontapi.dto.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Role {

    /* 1: MEMBER, 2: ADMIN */

    private Integer id;

    private String name;

    private String description;


}
