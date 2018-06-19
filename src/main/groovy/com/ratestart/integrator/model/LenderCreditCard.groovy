package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderCreditCard {

    Long idCreditCard
    Long lenderId
    String name
    BigDecimal purchase
    BigDecimal balance
    BigDecimal cashAdvance
    BigDecimal introApr
    String conditions
    String cardType
    String date
    String stateLicense
    String logoFileName

}
