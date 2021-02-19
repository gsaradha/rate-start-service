package com.ratestart.integrator.domain


enum CategoryType {
    UNKNOWN(null),
    HELOC(1),
    LOAN(2)

    Long id

    CategoryType(Long id){
        this.id = id
    }

    static CategoryType getCategoryType(Long id){
        CategoryType type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static CategoryType getCategoryType(String categoryType){
        CategoryType type =  values().find {it.name() == categoryType}
        !type? UNKNOWN : type
    }
}