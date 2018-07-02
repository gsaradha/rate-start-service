package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.CategoryConverter
import com.ratestart.integrator.domain.CategoryType
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.LoanTypeConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "HomeEquity")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class HomeEquity implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idHomeEquity")
    Long idHomeEquity

    @Basic(optional = false)
    @Column(name = "Lender_idLender_FK")
    Long lenderId

    @Basic(optional = false)
    @Column(name = "LoanType_idLoanType_FK")
    @Convert(converter = LoanTypeConverter)
    LoanType loanType

    @Column(name = "name")
    String name

    @Basic(optional = false)
    @Column(name = "rate")
    BigDecimal rate

    @Basic(optional = false)
    @Column(name = "credit_range")
    String credit

    @Basic(optional = false)
    @Column(name = "amount")
    BigDecimal amount

    @Basic(optional = false)
    @Column(name = "fees")
    BigDecimal fees

    @Basic(optional = false)
    @Column(name = "after_intro_rate")
    BigDecimal afterIntroRate

    @Basic(optional = false)
    @Column(name = "maxltv")
    Integer maxLtv

    @Basic(optional = false)
    @Column(name = "state_license")
    String stateLicense

    @Basic(optional = false)
    @Column(name = "required_draw")
    Integer requiredDraw

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date

    @Column(name = "logo_filename")
    String logoFilename

    @Column(name = "conditions")
    String conditions

    @Basic(optional = false)
    @Column(name = "HomeCategory_idHomeCategory_FK")
    @Convert(converter = CategoryConverter)
    CategoryType category

    @Column(name = "phone", insertable = false, updatable = false)
    String phone

}
