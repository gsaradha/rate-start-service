package com.ratestart.integrator.domain


enum LoanType {
    UNKNOWN(null),
    FIXED(1),
    ARM(2)

    Long id

    LoanType(Long id){
        this.id = id
    }

    static LoanType getLoanType(Long id){
        LoanType type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static LoanType getLoanType(String loanType){
        LoanType type =  values().find {it.name() == loanType}
        !type? UNKNOWN : type
    }
}