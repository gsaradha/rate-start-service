package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.AutoLoanTerm
import com.ratestart.integrator.domain.ProductOption
import com.ratestart.integrator.domain.ProductOptionConverter
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanOptionConverter
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.ProductOption
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "Auto")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class AutoEquity implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idAuto")
    Long idAuto

    @Basic(optional = false)
    @Column(name = "Lender_idLender_FK")
    Long idLender

    @Basic(optional = false)
    @Column(name = "LoanOption_idLoanOption_FK")
    @Convert(converter = LoanOptionConverter)
    LoanOption loanOption

    @Basic(optional = false)
    @Column(name = "rate")
    BigDecimal rate

    @Basic(optional = false)
    @Column(name = "apr")
    BigDecimal apr

    @Basic(optional = false)
    @Column(name = "credit")
    BigDecimal credit

    @Basic(optional = false)
    @Column(name = "credit_range")
    String creditRange

    @Basic(optional = false)
    @Column(name = "conditions")
    String conditions

    @Basic(optional = false)
    @Column(name = "loan_term")
    AutoLoanTerm loanTerm

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date

    @Basic(optional = false)
    @Column(name = "logo_filename")
    String logoFileName

    @Basic(optional = false)
    @Column(name = "base64_logo", insertable = false, updatable = false)
    String base64Logo

    @Basic(optional = false)
    @Column(name = "ProductCondition_idProductCondition_FK")
    @Convert(converter = ProductOptionConverter)
    ProductOption productCondition

}
