package com.onebook.frontapi.dto.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record Role(
        /* 1: MEMBER, 2: ADMIN */
        Integer id,
        String name,
        String description
){ }
