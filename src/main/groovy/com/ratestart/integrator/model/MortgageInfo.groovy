package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class MortgageInfo {
    Long mortgageId
    String name
    Long lenderId
    Long loanType
    Long loanOption
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


