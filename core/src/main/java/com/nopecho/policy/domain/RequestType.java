package com.nopecho.policy.domain;

import java.util.Arrays;

public enum RequestType {
    HTTP,
    NONE;

    public static RequestType of(String value) {
        return Arrays.stream(RequestType.values())
                .filter(type -> type.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[%s] 는 지원하지 않는 요청 타입 입니다.", value)));
    }
}
