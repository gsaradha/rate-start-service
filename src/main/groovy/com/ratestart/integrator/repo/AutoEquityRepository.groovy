package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface AutoEquityRepository extends CrudRepository<AutoEquity, Long> {

    @Query(value="""\
			   select a.idAuto,a.rate,a.apr,a.credit,a.credit_range,a.conditions,a.date,a.logo_filename,a.Lender_idLender_FK,a.LoanOption_idLoanOption_FK,
               o.desc,a.ProductCondition_idProductCondition_FK ,a.loan_term,l.base64_logo from Auto a join LoanOption o join ProductCondition p join lender l on
               a.Lender_idLender_FK=l.idLender and o.idLoanOption=a.LoanOption_idLoanOption_FK 
               and a.ProductCondition_idProductCondition_FK=p.idProductCondition where p.idProductCondition=:productConditionId and o.idLoanOption=:loanOptionId
               and a.loan_term=:loanTerm
            """, nativeQuery = true)
    List<AutoEquity> fetchAutoEquity(@Param("loanOptionId") Long loanOptionId, @Param("productConditionId") Long productConditionId, @Param("loanTerm") Long loanTerm)

}
