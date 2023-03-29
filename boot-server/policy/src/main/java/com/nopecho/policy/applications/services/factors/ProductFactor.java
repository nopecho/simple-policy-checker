package com.nopecho.policy.applications.services.factors;

import com.nopecho.policy.domain.Factor;
import com.nopecho.utils.JsonUtils;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFactor implements Factor {

    private String productId;

    @Override
    public String toJsonString() {
        return JsonUtils.get().toJson(this);
    }
}
