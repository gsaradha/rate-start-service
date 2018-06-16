package com.ratestart.integrator.domain


enum ProductOption {
    UNKNOWN(null),
    NEW(1),
    USED(2)

    Long id

    ProductOption(Long id){
        this.id = id
    }

    static ProductOption getProductOption(Long id){
        ProductOption type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static ProductOption getProductOption(String name){
        ProductOption type =  values().find {it.name() == name}
        !type? UNKNOWN : type
    }

}