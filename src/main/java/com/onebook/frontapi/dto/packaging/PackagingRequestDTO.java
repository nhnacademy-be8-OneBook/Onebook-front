package com.onebook.frontapi.dto.packaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PackagingRequestDTO {
    String name;
    int price;
}
