package com.ratestart.integrator.domain


enum AutoLoanTerm {
    UNKNOWN(null, null),
    TWO_YEAR(1, "2Y"),
    THREE_YEAR(2, "3Y"),
    FOUR_YEAR(3, "4Y"),
    FIVE_YEAR(4, "5Y")


    Long id
    String term

    AutoLoanTerm(Long id, String term){
        this.id = id
        this.term = term
    }

    static AutoLoanTerm getLoanTerm(Long id){
        AutoLoanTerm type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static AutoLoanTerm getLoanTerm(String loanTerm){
        AutoLoanTerm type =  values().find {it.name() == loanTerm}
        !type? UNKNOWN : type
    }

}