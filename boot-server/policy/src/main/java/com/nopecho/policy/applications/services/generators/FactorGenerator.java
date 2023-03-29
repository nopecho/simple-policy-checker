package com.nopecho.policy.applications.services.generators;

import com.nopecho.policy.applications.usecases.models.Request;
import com.nopecho.policy.domain.Factor;
import com.nopecho.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

public class FactorGenerator {

    private static final List<Class<? extends Factor>> supportedClass = List.of(
            Factor.class,
            ProductFactor.class,
            UserFactor.class,
            UserWithProductFactor.class
    );

    public static Factor gen(Request.FactorModel factorModel) {
        Object model = factorModel.getFactor();

        Class<? extends Factor> factorClass = supportedClass.stream()
                .filter(clazz -> filterSupportType(factorModel, clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원 되지 않는 Factor 타입 입니다."));

        return factorClass.cast(model);
    }

    private static boolean filterSupportType(Request.FactorModel model, Class<? extends Factor> clazz) {
        try {
            Object rawFactor = model.getFactor();
            String toJson = JsonUtils.get().toJson(rawFactor, clazz);
            return !Objects.isNull(toJson) && !toJson.isBlank();
        }catch (Exception e) {
            return false;
        }
    }
}
