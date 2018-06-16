
package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface LenderEquityRepository extends CrudRepository<Lender, Long> {

    @Query(value="""\
            select idLender,user_name, password, name,email,is_mortgage_lender,nmls_id,state_license,phone,logo_filename from
                lender where user_name=:userName and password=:password
            """, nativeQuery = true)
    Lender fetchLender(@Param("userName") String userName, @Param("password") String password)

    @Query(value="""\
            select idLender,user_name, password, name,email,is_mortgage_lender,nmls_id,state_license,phone,logo_filename from
                lender where user_name=:userName
            """, nativeQuery = true)
    Lender fetchExistingLenderUserName(@Param("userName") String userName)

}
