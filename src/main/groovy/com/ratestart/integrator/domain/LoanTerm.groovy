package com.ratestart.integrator.domain


enum LoanTerm {
    UNKNOWN(null, null),
    THIRTY_YEAR(1, "30Y"),
    TWENTY_YEAR(2, "20Y"),
    FIFTEEN_YEAR(3, "15Y"),
    TEN_YEAR(4, "10Y"),
    TEN_1ARM(5, "10/1ARM"),
    SEVEN_1ARM(6, "7/1ARM"),
    FIVE_1ARM(7, "5/1ARM"),
    THREE_1ARM(8, "3/1ARM"),
    ONE_YEAR(9, "1Y")

    Long id
    String term

    LoanTerm(Long id, String term){
        this.id = id
        this.term = term
    }

    static LoanTerm getLoanTerm(Long id){
        LoanTerm type =  values().find {it.id == id}
        !type? UNKNOWN : type
    }

    static LoanTerm getLoanTerm(String loanTerm){
        LoanTerm type =  values().find {it.name() == loanTerm}
        !type? UNKNOWN : type
    }

}