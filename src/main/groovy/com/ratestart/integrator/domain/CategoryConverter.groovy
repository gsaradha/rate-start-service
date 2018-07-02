package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter

class CategoryConverter implements AttributeConverter<CategoryType, Long>{


    @Override
    Long convertToDatabaseColumn(CategoryType attribute) {
        attribute.id
    }

    @Override
    CategoryType convertToEntityAttribute(Long dbData) {
        CategoryType.getCategoryType(dbData)
    }
}
