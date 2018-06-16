package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderInfo {
    Long idLender
    String userName
    String password
    String name
    String email
    Boolean isMortgageLender
    String nmlsId
    String stateLicense
    Integer phone
    String logoFileName
}
