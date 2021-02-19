package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter


class ProductOptionConverter implements AttributeConverter<ProductOption, Long>{

    @Override
    Long convertToDatabaseColumn(ProductOption attribute) {
        attribute.id
    }

    @Override
    ProductOption convertToEntityAttribute(Long dbData) {
        ProductOption.getProductOption(dbData)
    }
}
