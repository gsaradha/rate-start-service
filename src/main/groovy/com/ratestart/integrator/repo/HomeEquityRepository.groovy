package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface HomeEquityRepository extends CrudRepository<HomeEquity, Long> {

    @Query(value="""\
			select h.idHomeEquity,h.Lender_idLender_FK,h.name,h.rate,h.fees,h.credit_range,h.amount,h.after_intro_rate,
			h.maxltv,h.required_draw,h.logo_filename,h.conditions,h.date,t.description,l.state_license from 
            HomeEquity h join lender l join LoanType t on l.idLender=h.Lender_idLender_FK and 
            h.LoanType_idLoanType_FK=t.idLoanType where h.LoanType_idLoanType_FK=:loanTypeId order by h.rate
            """, nativeQuery = true)
    List<HomeEquity> fetchHomeEquity(@Param("loanTypeId") Long loanTypeId)

}
