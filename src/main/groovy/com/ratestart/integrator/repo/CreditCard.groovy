package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.CardType
import com.ratestart.integrator.domain.CardTypeConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "CreditCard")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class CreditCard implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idCreditCard")
    Long idCreditCard

    @Basic(optional = false)
    @Column(name = "Lender_idLender_FK")
    Long lenderId

    @Basic(optional = false)
    @Column(name = "CardType_idCardType_FK")
    @Convert(converter = CardTypeConverter)
    CardType cardType

    @Basic(optional = false)
    @Column(name = "intro_apr_purchase")
    BigDecimal purchase

    @Basic(optional = false)
    @Column(name = "intro_apr_balance")
    BigDecimal balance

    @Basic(optional = false)
    @Column(name = "cash_advance")
    BigDecimal cashAdvance

    @Basic(optional = false)
    @Column(name = "after_intro_apr")
    BigDecimal introApr

    @Basic(optional = false)
    @Column(name = "logo_filename")
    String logoFilename

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date

    @Column(name = "name")
    String name

    @Column(name = "conditions")
    String conditions

    @Column(name = "base64_logo", insertable = false, updatable = false)
    String base64Logo
}
