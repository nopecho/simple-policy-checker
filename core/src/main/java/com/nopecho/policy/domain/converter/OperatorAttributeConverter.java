package com.nopecho.policy.domain.converter;

import com.nopecho.policy.domain.Operator;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OperatorAttributeConverter implements AttributeConverter<Operator, String> {

    @Override
    public String convertToDatabaseColumn(Operator attribute) {
        return attribute.getValue();
    }

    @Override
    public Operator convertToEntityAttribute(String dbData) {
        return Operator.of(dbData);
    }
}
