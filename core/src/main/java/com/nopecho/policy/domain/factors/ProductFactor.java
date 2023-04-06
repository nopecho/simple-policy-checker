package com.nopecho.policy.domain.factors;

import com.nopecho.utils.JsonUtils;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFactor implements Factor {

    private String productId;

    @Override
    public String toJsonString() {
        return JsonUtils.get().toJson(this);
    }
}
