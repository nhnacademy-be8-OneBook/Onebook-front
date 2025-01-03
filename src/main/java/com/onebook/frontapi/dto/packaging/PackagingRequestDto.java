package com.onebook.frontapi.dto.packaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PackagingRequestDto {
    String name;
    int price;
}
