package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter

class BooleanConverter implements AttributeConverter<Boolean, Integer>{

    @Override
    Integer convertToDatabaseColumn(Boolean value) {
        value ? 1 : 0
    }

    @Override
    Boolean convertToEntityAttribute(Integer value) {
        1 == value
    }
}
