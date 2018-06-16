package com.ratestart.integrator.domain

import javax.persistence.AttributeConverter

class CardTypeConverter implements AttributeConverter<CardType, Long>{

    @Override
    Long convertToDatabaseColumn(CardType attribute) {
        attribute.id
    }

    @Override
    CardType convertToEntityAttribute(Long dbData) {
        CardType.getCardType(dbData)
    }
}
