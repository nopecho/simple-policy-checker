package com.nopecho.policy.domain;

import com.nopecho.policy.domain.factor.Factor;
import com.nopecho.utils.JsonUtils;
import com.nopecho.utils.Throw;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestTemplate {

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    private String url;

    private String body;

    private String accessField;

    public static RequestTemplate of(RequestType type,
                                     RequestMethod method,
                                     String url,
                                     String body,
                                     String accessField) {
        throwIfInvalidArgs(type, method, url, body, accessField);
        return new RequestTemplate(type, method, url, body, accessField);
    }

    public static RequestTemplate none() {
        return new RequestTemplate(RequestType.NONE,
                RequestMethod.NONE, RequestType.NONE.name(), RequestType.NONE.name(), RequestType.NONE.name());
    }

    RequestTemplate(RequestType requestType,
                    RequestMethod method,
                    String url,
                    String body,
                    String accessField) {
        throwIfInvalidArgs(requestType, method, url, body, accessField);
        this.requestType = requestType;
        this. method = method;
        this.url = url;
        this.body = body;
        this.accessField = accessField;
    }

    public String replaceBody(Factor factor, List<String> supports) {
        String result = this.body;
        supports.forEach(v -> JsonUtils.replaceJsonVariable(result, factor, v));
        return result;
    }

    private static void throwIfInvalidArgs(RequestType type,
                                           RequestMethod method,
                                           String url,
                                           String body,
                                           String accessField) {
        Throw.ifNull(type, "Request Template Type");
        Throw.ifNull(method, "Request Template Method");
        Throw.ifNullOrBlank(url, "Request Template url");
        Throw.ifNullOrBlank(accessField, "Request Template url accessField");
        if(RequestType.HTTP.equals(type)) {
            Throw.ifInvalidUrl(url);
        }
        if(body != null && !body.isEmpty()) {
            JsonUtils.validJsonFormat(body);
        }
    }
}
