package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderAutoEquity {
    Long idAuto
    Long lenderId
    BigDecimal rate
    BigDecimal apr
    BigDecimal credit
    String creditRange
    String conditions
    Date date
    String logoFileName
    String option
    String productCondition
    String stateLicense
}
