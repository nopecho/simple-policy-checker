package com.nopecho.policy.domain.utils;

import com.nopecho.policy.domain.OperatorType;
import com.nopecho.utils.KoreaClocks;

import java.util.Map;
import java.util.function.Function;

public class OperatorMapper {

    private static final Map<OperatorType, Function<String, Object>> objectContainer = Map.of(
            OperatorType.STRING, String::toString,
            OperatorType.NUMBER, Integer::parseInt,
            OperatorType.BOOL, Boolean::parseBoolean,
            OperatorType.DATE, KoreaClocks::convertToDate
    );

    public static Object convertToObject(OperatorType type, String value) {
        return objectContainer.get(type).apply(value);
    }
}
