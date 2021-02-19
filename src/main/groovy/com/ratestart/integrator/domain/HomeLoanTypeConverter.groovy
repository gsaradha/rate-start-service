package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter


class HomeLoanTypeConverter implements AttributeConverter<HomeLoanType, Long>{

    @Override
    Long convertToDatabaseColumn(HomeLoanType attribute) {
        attribute.id
    }

    @Override
    HomeLoanType convertToEntityAttribute(Long dbData) {
        HomeLoanType.getLoanType(dbData)
    }
}
