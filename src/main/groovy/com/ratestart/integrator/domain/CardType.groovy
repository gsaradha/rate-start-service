package com.ratestart.integrator.domain


enum CardType {
    UNKNOWN(null),
    APR(1),
    Balance(2),
    Travel(3),
    TopCards(4),
    CashBack(4),
    Rewards(5),
    GoodCredit(6),
    BadCredit(7)

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