package com.nopecho.policy.domain;

import com.nopecho.utils.JsonUtils;
import com.nopecho.utils.Throw;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestTemplate {

    @Enumerated(EnumType.STRING)
    private RequestOwner owner;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    private String url;

    private String body;

    private String accessField;

    public static RequestTemplate of(RequestOwner owner,
                                     RequestType type,
                                     RequestMethod method,
                                     String url,
                                     String body,
                                     String accessField) {
        if(RequestOwner.ACTION.equals(owner)) {
            return new RequestTemplate(owner, type, method, url, body, "NONE");
        }
        return new RequestTemplate(owner, type, method, url, body, accessField);
    }

    public static RequestTemplate none(RequestOwner owner) {
        return new RequestTemplate(owner, RequestType.NONE,
                RequestMethod.NONE, RequestType.NONE.name(), RequestType.NONE.name(), RequestType.NONE.name());
    }

    RequestTemplate(RequestOwner owner,
                    RequestType requestType,
                    RequestMethod method,
                    String url,
                    String body,
                    String accessField) {
        throwIfInvalidArgs(owner, requestType, method, url, body, accessField);
        this.owner = owner;
        this.requestType = requestType;
        this. method = method;
        this.url = url;
        this.body = body;
        this.accessField = accessField;
    }

    public String replaceVariableBody(Factor factor, Set<String> variables) {
        String result = this.body;
        for (String variable : variables) {
            result = JsonUtils.replaceJsonVariable(result, factor, variable);
        }
        return result;
    }

    public String toString() {
        return String.format("RequestOwner:[%s],  RequestMethod:[%s], URL:[%s], BODY:[%s]",
                this.owner.name(), this.method.name(), this.url, this.body);
    }

    private static void throwIfInvalidArgs(RequestOwner owner,
                                           RequestType type,
                                           RequestMethod method,
                                           String url,
                                           String body,
                                           String accessField) {
        Throw.ifNull(owner, "Request Template Owner");
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
