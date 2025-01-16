package com.onebook.frontapi.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final CommonHeader header;
    private final T result;

    @Builder
    public CommonResponse(CommonHeader header, T result) {
        this.header = header;
        this.result = result;
    }
}
//API 응답의 구조를 나타내는 클래스