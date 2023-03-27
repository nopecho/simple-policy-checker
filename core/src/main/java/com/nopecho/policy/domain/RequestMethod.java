package com.nopecho.policy.domain;

import java.util.Arrays;

public enum RequestMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    NONE;

    public static RequestMethod of(String value) {
        return Arrays.stream(RequestMethod.values())
                .filter(type -> type.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[%s] 는 지원하지 않는 메서드 입니다.", value)));
    }
}
