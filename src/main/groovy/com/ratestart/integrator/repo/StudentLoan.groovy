package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanOptionConverter
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.domain.LoanTypeConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "StudentLoan")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class StudentLoan implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idStudentLoan")
    Long idStudentLoan

    @Basic(optional = false)
    @Column(name = "StudentLoanType_idStudentLoanType_FK")
    @Convert(converter = LoanTypeConverter)
    LoanType studentLoanType

    @Basic(optional = false)
    @Column(name = "Lender_idLender_FK")
    Long lenderId

    @Basic(optional = false)
    @Column(name = "conditions")
    String conditions

    @Basic(optional = false)
    @Column(name = "student_loan_col")
    String studentLoanCol


    @Basic(optional = false)
    @Column(name = "apr")
    BigDecimal apr

    @Basic(optional = false)
    @Column(name = "credit_range")
    String credit

    @Basic(optional = false)
    @Column(name = "state_license")
    String stateLicense

    @Basic(optional = false)
    @Column(name = "logo_filename")
    String logoFileName

    @Basic(optional = false)
    @Column(name = "studentloandesc")
    String studentLoanDesc


}
