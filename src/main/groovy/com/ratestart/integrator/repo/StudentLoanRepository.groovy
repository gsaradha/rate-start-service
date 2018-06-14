package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface StudentLoanRepository extends CrudRepository<StudentLoan, Long> {

    @Query(value="""\
			    
                select s.idStudentLoan,s.StudentLoanType_idStudentLoanType_FK,s.Lender_idLender_FK,s.conditions,s.student_loan_col,s.apr,s.credit_range,
                l.state_license,l.logo_filename,stu.StudentLoanDesc from studentloan s join lender l join studentloantype stu on 
                s.idStudentLoan=l.idLender and s.studentloantype_idstudentloantype_fk=stu.idStudentLoanType and 
                s.StudentLoanType_idStudentLoanType_FK=:studentLoan;
                order by s.apr
            """, nativeQuery = true)
    List<StudentLoan> fetchStudentLoan(@Param("studentLoan") Long studentLoan)

}
