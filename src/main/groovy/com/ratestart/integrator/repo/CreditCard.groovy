package com.ratestart.integrator.repo

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

   /* @Basic(optional = false)
    @Column(name = "LoanType_idLoanType_FK")
    @Convert(converter = LoanTypeConverter)
    LoanType loanType*/

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

    @Column(name = "conditions")
    String conditions

    @Basic(optional = false)
    @Column(name = "description")
    String cardType

    @Basic(optional = false)
    @Column(name = "state_license")
    String stateLicense


}
