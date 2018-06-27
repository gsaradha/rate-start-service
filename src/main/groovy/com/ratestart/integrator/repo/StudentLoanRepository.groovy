package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface StudentLoanRepository extends CrudRepository<StudentLoan, Long> {

    @Query(value="""\
			    select s.idStudentLoan,s.StudentLoanType_idStudentLoanType_FK,s.Lender_idLender_FK,s.conditions,s.student_loan_col,
			    s.apr,s.credit_range,s.name,s.loan_term,l.logo_filename,l.phone from StudentLoan s join lender l join StudentLoanType stu on 
                s.idStudentLoan=l.idLender and s.StudentLoanType_idStudentLoanType_fk=stu.idStudentLoanType and 
                s.StudentLoanType_idStudentLoanType_FK=:loanTypeId
                order by s.apr
            """, nativeQuery = true)
    List<StudentLoan> fetchStudentLoan(@Param("loanTypeId") Long loanTypeId)

}
