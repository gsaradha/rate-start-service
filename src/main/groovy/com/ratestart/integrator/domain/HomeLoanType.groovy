package com.ratestart.integrator.domain


enum HomeLoanType {
    UNKNOWN(null),
    HELOC(1),
    EQUITY(2)

    Long id

    HomeLoanType(Long id){
        this.id = id
    }

    static HomeLoanType getLoanType(Long id){
        HomeLoanType type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static HomeLoanType getLoanType(String loanType){
        HomeLoanType type =  values().find {it.name() == loanType}
        !type? UNKNOWN : type
    }
}