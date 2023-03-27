package com.nopecho.policy.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public enum OperatorType {
    STRING("string"),
    NUMBER("number"),
    BOOL("bool"),
    DATE("date");

    private String value;

    public String get() {
        return this.value;
    }

    public static OperatorType of(String value) {
        return Arrays.stream(OperatorType.values())
                .filter(type -> type.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[%s] 지원 되지 않는 연산 타입 입니다.", value)));
    }
}
