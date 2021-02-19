package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter


class LoanTypeConverter implements AttributeConverter<LoanType, Long>{

    @Override
    Long convertToDatabaseColumn(LoanType attribute) {
        attribute.id
    }

    @Override
    LoanType convertToEntityAttribute(Long dbData) {
        LoanType.getLoanType(dbData)
    }
}
