package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter


class LoanTermConverter implements AttributeConverter<LoanTerm, Long>{

    @Override
    Long convertToDatabaseColumn(LoanTerm attribute) {
        attribute.id
    }

    @Override
    LoanTerm convertToEntityAttribute(Long dbData) {
        LoanTerm.getLoanTerm(dbData)
    }
}
