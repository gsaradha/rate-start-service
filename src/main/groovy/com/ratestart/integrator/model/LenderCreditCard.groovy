package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.CardType
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
    CardType cardType
    String date
    String stateLicense
    String logoFileName

}
