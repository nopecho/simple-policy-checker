package com.nopecho.policy.applications.services.generators;

import com.nopecho.policy.applications.ports.in.models.Request;
import com.nopecho.policy.domain.factors.Factor;
import com.nopecho.policy.domain.factors.ProductFactor;
import com.nopecho.policy.domain.factors.UserFactor;
import com.nopecho.policy.domain.factors.UserWithProductFactor;
import com.nopecho.utils.JsonUtils;

import java.util.List;

public class FactorGenerator {

    private static final List<Class<? extends Factor>> supportedClass = List.of(
            ProductFactor.class,
            UserFactor.class,
            UserWithProductFactor.class
    );

    public static Factor gen(Request.FactorModel factorModel) {
        Object model = factorModel.getFactor();
        String toJson = JsonUtils.get().toJson(model);

        Class<? extends Factor> factorClass = supportedClass.stream()
                .filter(clazz -> filterSupportType(toJson, clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("지원 되지 않는 Factor 입니다. factor: %s", toJson)));

        return JsonUtils.get().fromJson(toJson,factorClass);
    }

    private static boolean filterSupportType(String toJson, Class<? extends Factor> clazz) {
        try {
            Factor factor = JsonUtils.get().fromJson(toJson, clazz);
            String convertedFactor = factor.toJsonString();
            return toJson.equals(convertedFactor);
        }catch (Exception e) {
            return false;
        }
    }
}
