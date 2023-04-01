package com.nopecho.policy.applications.ports.in.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nopecho.policy.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public interface Response {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class PolicyResult {
        Long policyId;
        Boolean result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class PolicyModel {

        Long id;

        @NotNull
        @NotBlank
        String name;

        String description;

        @NotNull
        @NotEmpty
        Set<Response.StatementModel> statements;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") //ISO 8601
        LocalDateTime createdAt;

        public static PolicyModel toModel(Policy policy) {
            Set<StatementModel> statementModels = policy.getStatements().stream()
                    .map(StatementModel::toModel)
                    .collect(Collectors.toSet());

            return new PolicyModel(policy.getPolicyId(), policy.getName(), policy.getDescription(), statementModels, policy.getCreatedAt());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class StatementModel {
        @NotNull
        Set<String> supportFactorKeys;

        @NotNull
        @NotEmpty
        Set<Response.ActionModel> actions;

        @NotNull
        @NotEmpty
        Set<Response.ConditionModel> conditions;

        public static StatementModel toModel(Statement statement) {
            Set<ConditionModel> conditionModels = statement.getConditions().stream()
                    .map(ConditionModel::toModel)
                    .collect(Collectors.toSet());

            Set<ActionModel> actionModels = statement.getActions().stream()
                    .map(ActionModel::toModel)
                    .collect(Collectors.toSet());

            return new StatementModel(statement.getFactorKeys(), actionModels, conditionModels);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ActionModel {
        @NotNull
        @NotBlank
        String name;

        @NotNull
        @NotBlank
        String type;

        @NotNull
        Response.TemplateModel template;

        public static ActionModel toModel(Action action) {
            TemplateModel templateModel = TemplateModel.toModel(action.getTemplate());
            return new ActionModel(action.getName(), action.getActionType().name(), templateModel);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ConditionModel {
        @NotNull
        @NotBlank
        String name;

        String description;

        @NotNull
        @NotEmpty
        Set<Response.SpecModel> specs;

        public static ConditionModel toModel(Condition condition) {
            Set<SpecModel> specModels = condition.getSpecs().stream()
                    .map(SpecModel::toModel)
                    .collect(Collectors.toSet());

            return new ConditionModel(condition.getName(), condition.getDescription(), specModels);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class SpecModel {
        @NotNull
        Response.ActualModel actual;

        @NotNull
        @NotBlank
        String operator;

        @NotNull
        Response.ExpectModel expect;

        public static SpecModel toModel(Spec spec) {
            ActualModel actualModel = ActualModel.toModel(spec.getActual());
            ExpectModel expectModel = ExpectModel.toModel(spec.getExpect());
            return new SpecModel(actualModel, spec.getOperator().getValue(), expectModel);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ActualModel {
        String description;

        @NotNull
        @NotBlank
        String operatorType;

        @NotNull
        Response.TemplateModel value;


        public static ActualModel toModel(SpecActual specActual) {
            TemplateModel templateModel = TemplateModel.toModel(specActual.getTemplate());
            return new ActualModel(specActual.getDescription(), specActual.getOperatorType().name(), templateModel);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ExpectModel {
        @NotNull
        @NotBlank
        String operatorType;

        @NotNull
        @NotBlank
        String value;

        public static ExpectModel toModel(SpecExpect specExpect) {
            return new ExpectModel(specExpect.getOperatorType().name(), specExpect.getExpectValue());
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class TemplateModel {
        @NotNull
        @NotBlank
        String method;

        @NotNull
        @NotBlank
        String url;

        @NotNull
        @NotBlank
        String body;

        String accessField;

        public static TemplateModel toModel(RequestTemplate template) {
            return new TemplateModel(
                    template.getMethod().name(), template.getUrl(), template.getBody(), template.getAccessField()
            );
        }
    }
}
