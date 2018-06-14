package com.ratestart.integrator.domain


enum StudentLoanType {
    UNKNOWN(null),
    FIXED(1),
    VARIABLE(2)

    Long id

    StudentLoanType(Long id){
        this.id = id
    }

    static StudentLoanType getLoanType(Long id){
        StudentLoanType type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

}