package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.StudentLoanType
import com.ratestart.integrator.domain.StudentLoanTypeConverter
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
    @Column(name = "Lender_idLender_FK")
    Long lenderId

    @Basic(optional = false)
    @Column(name = "StudentLoanType_idStudentLoanType_FK")
    @Convert(converter = StudentLoanTypeConverter)
    StudentLoanType studentLoanType

    @Basic(optional = false)
    @Column(name = "name")
    String name

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
    String creditRange

    @Basic(optional = false)
    @Column(name = "loan_term")
    String loanTerm

    @Column(name = "logo_filename", insertable = false, updatable = false)
    String logoFilename

    @Column(name = "base64_logo", insertable = false, updatable = false)
    String base64Logo

    @Column(name = "phone", insertable = false, updatable = false)
    String phone

}
