package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    @Query(value="""\
			select c.idCreditCard,c.name,c.Lender_idLender_FK,c.intro_apr_purchase,c.intro_apr_balance,c.cash_advance,
			c.after_intro_apr,c.logo_filename,c.date,c.conditions,c.CardType_idCardType_FK,l.base64_logo
			from CreditCard c join Lender l join CardType t on l.idLender=c.Lender_idLender_FK and c.CardType_idCardType_FK=t.idCardType
            where CardType_idCardType_FK=:cardTypeId order by c.after_intro_apr
            """, nativeQuery = true)
    List<CreditCard>fetchCreditCard(@Param("cardTypeId") Long cardTypeId)

}
