package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanOptionConverter
import com.ratestart.integrator.domain.LoanTerm
import com.ratestart.integrator.domain.LoanTermConverter
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.LoanTypeConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "Mortgage")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Mortgage implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idMortgage")
    Long idMortgage

    @Basic(optional = false)
    @Column(name = "fees")
    BigDecimal fees

    @Basic(optional = false)
    @Column(name = "points")
    Integer points

    @Basic(optional = false)
    @Column(name = "apr")
    BigDecimal apr

    @Basic(optional = false)
    @Column(name = "rate_period")
    Integer ratePeriod

    @Basic(optional = false)
    @Column(name = "nmls_id")
    String nmlsId

    @Basic(optional = false)
    @Column(name = "logo_filename")
    String logoFileName

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date

    @Basic(optional = false)
    @Column(name = "Lender_idLender_FK")
    Long idLender

    @Basic(optional = false)
    @Column(name = "LoanType_idLoanType_FK")
    @Convert(converter = LoanTypeConverter)
    LoanType loanType

    @Basic(optional = false)
    @Column(name = "LoanOption_idLoanOption_FK")
    @Convert(converter = LoanOptionConverter)
    LoanOption loanOption

    @Basic(optional = false)
    @Column(name = "LoanTerm_idLoanTerm_FK")
    @Convert(converter = LoanTermConverter)
    LoanTerm loanTerm

    @Basic(optional = false)
    @Column(name = "name")
    String name

    @Basic(optional = false)
    @Column(name = "monthly_pay")
    BigDecimal monthlyPay

    @Basic(optional = false)
    @Column(name = "base64_logo", insertable = false, updatable = false)
    String base64Logo

}
