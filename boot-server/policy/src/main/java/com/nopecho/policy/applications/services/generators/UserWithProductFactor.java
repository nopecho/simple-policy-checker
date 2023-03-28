package com.nopecho.policy.applications.services.generators;

import com.nopecho.policy.domain.Factor;
import com.nopecho.utils.JsonUtils;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserWithProductFactor implements Factor {

    String userId;
    String productId;

    @Override
    public String toJsonString() {
        return JsonUtils.get().toJson(this);
    }
}
