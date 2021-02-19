package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface HomeEquityRepository extends CrudRepository<HomeEquity, Long> {

    @Query(value="""\
			select he.idHomeEquity,he.Lender_idLender_FK,he.LoanType_idLoanType_FK, he.name,he.rate,he.fees, 
			he.credit_range,he.amount,he.after_intro_rate,
            he.maxltv,he.state_license,he.required_draw,he.date,he.logo_filename,he.conditions,he.HomeCategory_idHomeCategory_FK,
            l.phone,l.base64_logo from HomeEquity he join Lender l  join LoanType t join HomeCategory h on he.LoanType_idLoanType_FK=t.idLoanType 
            and l.idLender=he.Lender_idLender_FK 
            and h.idHomeCategory=he.HomeCategory_idHomeCategory_FK where 
            he.homecategory_idhomecategory_fk=:categoryId order by he.rate
            """, nativeQuery = true)
    List<HomeEquity> fetchHomeEquity(@Param("categoryId") Long categoryId )

}
