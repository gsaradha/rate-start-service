
package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface LenderEquityRepository extends CrudRepository<Lender, Long> {

    @Query(value="""\
   
            select idLender,name,email,is_mortgage_lender,nmls_id,state_license,phone,logo_filename from
                lender where user_name=:username and password=:password
            """, nativeQuery = true)
    List<Lender> fetchLender(@Param("username") String username, @Param("password") String password)

}
