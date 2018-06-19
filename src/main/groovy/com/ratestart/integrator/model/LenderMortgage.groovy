package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderMortgage {

    Long mortgageId
    String name
    Long lenderId
    String loanType
    String loanOption
    BigDecimal fees
    Integer points
    BigDecimal apr
    Integer ratePeriod
    BigDecimal monthlyPay
    String date
    String nmlsId
    String stateLicense
    String logoFileName

}
