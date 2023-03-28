package com.nopecho.policy.domain;

import com.nopecho.policy.domain.utils.OperatorMapper;
import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "policy_spec_actual")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecActual {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "actual_operator_type")
    @Enumerated(EnumType.STRING)
    private OperatorType operatorType;

    @Embedded
    private RequestTemplate template;

    private String tempResult;

    public static SpecActual of(OperatorType type, RequestTemplate template, String description) {
        throwIfInvalidArgs(type, template);
        return new SpecActual(null, Objects.requireNonNull(description, "비교값"), type, template, null);
    }

    public <T> T getActualValue(Class<T> classOfT) {
        try {
            Object toObject = OperatorMapper.convertToObject(this.operatorType, this.getResultIfNilThrow());
            return classOfT.cast(toObject);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("[%s] > [%s] 변환 실패::message:%s", this.tempResult, classOfT.getSimpleName(), e.getMessage()));
        }
    }

    public String getResultIfNilThrow() {
        if(this.tempResult.isBlank() || this.tempResult == null) {
            throw new IllegalStateException("결과를 먼저 할당 해야합니다.");
        }
        return this.tempResult;
    }

    public void setResult(String result) {
        this.tempResult = result;
    }

    public void resetResult() {
        this.tempResult = null;
    }

    private static void throwIfInvalidArgs(OperatorType type, RequestTemplate template) {
        Throw.ifNull(type, "Operator Type");
        Throw.ifNull(template, "Spec Actual template");
        if(RequestType.HTTP.equals(template.getRequestType())) {
            Throw.ifInvalidUrl(template.getUrl());
        }
    }
}
