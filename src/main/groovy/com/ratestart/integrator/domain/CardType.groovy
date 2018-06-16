package com.ratestart.integrator.domain


enum CardType {
    UNKNOWN(null),
    FIXED(1),
    ARM(2)

    Long id

    CardType(Long id){
        this.id = id
    }

    static CardType getCardType(Long id){
        CardType type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static CardType getCardType(String cardType){
        CardType type =  values().find {it.name() == cardType}
        !type? UNKNOWN : type
    }
}