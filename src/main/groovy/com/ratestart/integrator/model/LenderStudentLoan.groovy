package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.StudentLoanType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderStudentLoan {

    Long idStudentLoan
    StudentLoanType studentLoanType
    Long lenderId
    String conditions
    String studentLoanCol
    BigDecimal apr
    String creditRange
    String stateLicense
    String logoFileName
    String studentLoanDesc
}
