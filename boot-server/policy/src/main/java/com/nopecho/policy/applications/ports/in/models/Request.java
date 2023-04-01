package com.nopecho.policy.applications.ports.in.models;

import com.nopecho.policy.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public interface Request {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class FactorModel {
        @NotNull
        Long policyId;

        @NotNull
        Object factor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class PolicyModel {
        @NotNull
        @NotBlank
        String name;

        String description;

        @NotNull
        @NotEmpty
        Set<StatementModel> statements;

        public void validate() {
            this.statements.forEach(StatementModel::validate);
        }

        public Policy toEntity() {
            Policy policy = Policy.of(this.name, this.description);
            this.statements.stream()
                    .map(StatementModel::toEntity)
                    .forEach(policy::addStatement);

            return policy;
        }

        public Set<Statement> toStatements() {
            return this.statements.stream()
                    .map(StatementModel::toEntity)
                    .collect(Collectors.toSet());
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
        Set<ActionModel> actions;

        @NotNull
        @NotEmpty
        Set<ConditionModel> conditions;

        public void validate() {
            this.conditions.forEach(ConditionModel::validate);
        }

        public Statement toEntity() {
            Statement statement = Statement.of(this.supportFactorKeys);

            this.actions.stream()
                    .map(ActionModel::toEntity)
                    .forEach(statement::addAction);

            this.conditions.stream()
                    .map(ConditionModel::toEntity)
                    .forEach(statement::addCondition);

            return statement;
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
        TemplateModel template;

        public Action toEntity() {
            RequestTemplate requestTemplate = RequestTemplate.builder()
                    .owner(RequestOwner.ACTION)
                    .requestType(RequestType.HTTP)
                    .method(RequestMethod.of(this.template.method))
                    .url(this.template.url)
                    .body(this.template.body)
                    .accessField("NONE").build();
            return Action.ofWebHook(this.name, requestTemplate);
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
        Set<SpecModel> specs;

        public void validate() {
            this.specs.forEach(SpecModel::validate);
        }

        public Condition toEntity() {
            Condition condition = Condition.of(this.name, this.description);

            this.specs.stream()
                    .map(SpecModel::toEntity)
                    .forEach(condition::addSpec);

            return condition;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class SpecModel {
        @NotNull
        ActualModel actual;

        @NotNull
        @NotBlank
        String operator;

        @NotNull
        ExpectModel expect;

        public void validate() {
            if(!actual.getOperatorType().equals(expect.getOperatorType())) {
                throw new IllegalArgumentException(String.format("[%s] != [%s] 같은 타입만 연산이 가능합니다.", actual.getOperatorType(), expect.getOperatorType()));
            }
        }

        public Spec toEntity() {
            SpecActual specActual = this.actual.toEntity();
            SpecExpect specExpect = this.expect.toEntity();

            return Spec.of(specActual, Operator.of(operator), specExpect);
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
        TemplateModel value;

        public SpecActual toEntity() {
            RequestTemplate requestTemplate = RequestTemplate.builder()
                    .owner(RequestOwner.SPEC)
                    .requestType(RequestType.HTTP)
                    .method(RequestMethod.of(this.value.method))
                    .url(this.value.url)
                    .body(this.value.body)
                    .accessField(this.value.accessField).build();

            return SpecActual.of(OperatorType.of(this.operatorType), requestTemplate, description);
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

        public SpecExpect toEntity() {
            return SpecExpect.of(OperatorType.of(this.operatorType), value);
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
    }
}
