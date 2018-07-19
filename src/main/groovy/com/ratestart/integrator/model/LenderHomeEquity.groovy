package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.HomeLoanType
import com.ratestart.integrator.domain.LoanType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderHomeEquity {
    Long idHomeEquity
    Long lenderId
    HomeLoanType loanType
    String name
    BigDecimal rate
    String credit
    BigDecimal amount
    BigDecimal fees
    BigDecimal afterIntroRate
    Integer maxLtv
    String stateLicense
    Integer requiredDraw
    String date
    String logoFileName
    String conditions
    String category
    String phone
    String base64Logo
}
