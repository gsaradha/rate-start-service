package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter

class StudentLoanTypeConverter implements AttributeConverter<StudentLoanType, Long>{

    @Override
    Long convertToDatabaseColumn(StudentLoanType attribute) {
        attribute.id
    }

    @Override
    StudentLoanType convertToEntityAttribute(Long dbData) {
        StudentLoanType.getLoanType(dbData)
    }
}
