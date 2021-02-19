package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter


class LoanOptionConverter implements AttributeConverter<LoanOption, Long>{

    @Override
    Long convertToDatabaseColumn(LoanOption attribute) {
        attribute.id
    }

    @Override
    LoanOption convertToEntityAttribute(Long dbData) {
        LoanOption.getLoanOption(dbData)
    }
}
