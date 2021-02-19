package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.AutoLoanTerm
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.ProductOption
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderAutoEquity {
    Long idAuto
    Long lenderId
    LoanOption loanOption
    AutoLoanTerm loanTerm
    BigDecimal rate
    BigDecimal apr
    BigDecimal credit
    String creditRange
    String conditions
    String date
    String logoFileName
    String option
    ProductOption productCondition
    String stateLicense
    String base64Logo
}
