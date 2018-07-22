
package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository extends CrudRepository<User, Long> {

    @Query(value="""\
            select idUser,email,password,name,subscribed from User where email=:email and password=:password
            """, nativeQuery = true)
    User fetchUser(@Param("email") String email, @Param("password") String password)

    @Query(value="""\
            select idUser,email,password,name,subscribed from User where email=:email 
            """, nativeQuery = true)
    Lender fetchExistingUser(@Param("email") String email)

}
