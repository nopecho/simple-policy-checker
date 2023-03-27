package com.nopecho.policy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Operator {

    /**
     * this enum is defined operator for condition check.
     * operator naming rule : {type}.{operator}.{operator} ...
     */
    STRING_EQUALS(
            OperatorType.STRING,
            OperatorType.STRING.get() + ".equals",
            (actual, expect) -> Objects.equals(
                    actual.getActualValue(String.class),
                    expect.getExpectValue(String.class))
    ),
    STRING_LIKE(
            OperatorType.STRING,
            OperatorType.STRING.get() + ".like",
            (actual, expect) -> actual.getActualValue(String.class)
                    .contains(expect.getExpectValue(String.class))
    ),

    NUMBER_EQUALS(
            OperatorType.NUMBER,
            OperatorType.NUMBER.get() + ".equals",
            (actual, expect) -> Objects.equals(
                    actual.getActualValue(Integer.class),
                    expect.getExpectValue(Integer.class))
    ),
    NUMBER_LESS_THAN(
            OperatorType.NUMBER,
            OperatorType.NUMBER.get() + ".lessThan",
            (actual, expect) ->
                    actual.getActualValue(Integer.class) < expect.getExpectValue(Integer.class)
    ),
    NUMBER_LESS_THAN_EQUALS(
            OperatorType.NUMBER,
            OperatorType.NUMBER.get() + ".lessThan.equals",
            (actual, expect) ->
                    actual.getActualValue(Integer.class) <= expect.getExpectValue(Integer.class)
    ),
    NUMBER_GREATER_THAN(
            OperatorType.NUMBER,
            OperatorType.NUMBER.get() + ".greaterThan",
            (actual, expect) ->
                    actual.getActualValue(Integer.class) > expect.getExpectValue(Integer.class)
    ),
    NUMBER_GREATER_THAN_EQUALS(
            OperatorType.NUMBER,
            OperatorType.NUMBER.get() + ".greaterThan.equals",
            (actual, expect) ->
                    actual.getActualValue(Integer.class) >= expect.getExpectValue(Integer.class)
    ),

    DATE_IS_AFTER(
            OperatorType.DATE,
            OperatorType.DATE.get() + ".isAfter",
            (actual, expect) ->
                    actual.getActualValue(ZonedDateTime.class).isAfter(expect.getExpectValue(ZonedDateTime.class))
    ),
    DATE_IS_BEFORE(
            OperatorType.DATE,
            OperatorType.DATE.get() + ".isBefore",
            (actual, expect) ->
                    actual.getActualValue(ZonedDateTime.class).isBefore(expect.getExpectValue(ZonedDateTime.class))
    ),
    DATE_EQUALS(
            OperatorType.DATE,
            OperatorType.DATE.get() + ".isBefore",
            (actual, expect) ->
                    actual.getActualValue(ZonedDateTime.class).isEqual(expect.getExpectValue(ZonedDateTime.class))
    ),

    BOOL(OperatorType.BOOL, OperatorType.BOOL.get(),
            (actual, expect) -> Objects.equals(
                    actual.getActualValue(Boolean.class),
                    expect.getExpectValue(Boolean.class)));

    private OperatorType type;
    private String value;
    private BiPredicate<SpecActualDetail, SpecExpect> isSatisfy;

    public BiPredicate<SpecActualDetail, SpecExpect> isSatisfy() {
        return this.isSatisfy;
    }

    public static Operator of(String value) {
        return Arrays.stream(Operator.values())
                .filter(operator -> operator.getValue().equals(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("[%s] 는 지원하지 않는 연산자 입니다.", value)));
    }
}
