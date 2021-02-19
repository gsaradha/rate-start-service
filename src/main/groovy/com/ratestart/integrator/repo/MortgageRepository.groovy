package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MortgageRepository extends CrudRepository<Mortgage, Long> {

    @Query(value="""\
			select m.idMortgage,m.Lender_idLender_FK,m.LoanType_idLoanType_FK,m.LoanOption_idLoanOption_FK,m.LoanTerm_idLoanTerm_FK,
            m.fees,m.points,m.apr,m.rate_period,m.nmls_id,m.logo_fileName,m.date,m.monthly_pay,m.name,o.desc,t.description,l.base64_logo
            from Mortgage m join Lender l join LoanOption o join LoanType t on l.idLender=m.Lender_idLender_FK 
            and o.idLoanOption=m.LoanOption_idLoanOption_FK and t.idLoanType=m.LoanType_idLoanType_FK 
            where LoanType_idLoanType_FK=:loanTypeId and LoanOption_idLoanOption_FK=:loanOptionId and LoanTerm_idLoanTerm_FK=:loanTermId
            order by m.rate_period
            """, nativeQuery = true)
    List<Mortgage> fetchMortgages(@Param("loanTypeId") Long loanTypeId,@Param("loanOptionId") Long loanOptionId,@Param("loanTermId") Long loanTermId)

}
