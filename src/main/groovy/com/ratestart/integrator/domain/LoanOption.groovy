package com.ratestart.integrator.domain


enum LoanOption {
    UNKNOWN(null),
    PURCHASE(1),
    REFINANCE(2)

    Long id

    LoanOption(Long id){
        this.id = id
    }

    static LoanOption getLoanOption(Long id){
        LoanOption type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

}