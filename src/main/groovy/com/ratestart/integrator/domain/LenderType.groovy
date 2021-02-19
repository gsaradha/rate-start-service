package com.ratestart.integrator.domain

enum LenderType {

    AUTO(1, "Automobile rate has been changed to "),
    CREDIT(2, "Credit rate has been changed to "),
    MORTGAGE(3, "Mortgage rate has been changed to "),
    STUDENT(4, "Student rate has been changed to "),
    HOME(5, "Home Equity rate has been changed to "),
    ALL(6, "All")

    Integer id
    String message

    LenderType(Integer id, String message) {
        this.id = id
        this.message = message
    }

}
