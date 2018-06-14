package com.ratestart.integrator.controllers

import com.newrelic.api.agent.Trace
import com.ratestart.integrator.model.Lender
import com.ratestart.integrator.model.LenderAutoEquity
import com.ratestart.integrator.model.LenderHomeEquity
import com.ratestart.integrator.model.LenderMortgage
import com.ratestart.integrator.model.LenderStudentLoan
import com.ratestart.integrator.repo.StudentLoan
import com.ratestart.integrator.services.RateStartService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Log4j
@RestController
class RateStartController {

    @Autowired
    RateStartService rateStartService

    @Trace
    @RequestMapping(value = "/saveLender", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void processLead(@Valid @RequestBody String classObject, BindingResult bindingResult) throws Exception {
        log.info("Update Received, preparing to process lender of: ${classObject}")
        if(bindingResult.hasErrors()) {
            throw new Exception("Invalid Lender encountered - ${bindingResult}")
        }

        //Call the Service method to process
        log.info("Lead finished processing for web publisher campaign of: ${classObject}")
    }

    @RequestMapping(value = "/lenderMortgages/loanType/{loanTypeId}/loanOption/{loanOptionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderMortgage> getLenderMortgages(@PathVariable("loanTypeId") Long loanTypeId,@PathVariable("loanOptionId") Long loanOptionId) throws Exception {
        Optional<List<LenderMortgage>> mortgageList = rateStartService.getLenderMortgages(loanTypeId,loanOptionId)
        mortgageList.isPresent() ? mortgageList.get() : null
    }

    @RequestMapping(value = "/lenderHomeEquity/loanType/{loanTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderHomeEquity> getLenderHomeEquity(@PathVariable("loanTypeId") Long loanTypeId) throws Exception {
        Optional<List<LenderHomeEquity>> homeEquityList = rateStartService.getLenderHomeEquity(loanTypeId)
        homeEquityList.isPresent() ? homeEquityList.get() : null
    }

    @RequestMapping(value = "/lenderAutoEquity/loanOption/{loanOptionId}/productConditionId/{productConditionId}/loanTerm/{loanTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderAutoEquity> getLenderAutoEquity(@PathVariable("loanOptionId") Long loanOptionId, @PathVariable("productConditionId") Long productConditionId, @PathVariable("loanTerm") Long loanTerm) throws Exception {
        Optional<List<LenderAutoEquity>> autoEquityList = rateStartService.getAutoEquity(loanOptionId, productConditionId,loanTerm)
        autoEquityList.isPresent() ? autoEquityList.get() : null
    }

    @RequestMapping(value = "/lender/username/{username}/password/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Lender> getLender(@PathVariable("username") String username, @PathVariable("password") String password) throws Exception {
        Optional<List<Lender>> lenderList = rateStartService.getLender(username,password)
        lenderList.isPresent() ? lenderList.get() : null
    }
    @RequestMapping(value = "/lenderStudentLoan/studentLoanType/{studentLoanType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderStudentLoan> getLenderStudentLoan(@PathVariable("studentLoanType") Long studentLoanType) throws Exception {
        Optional<List<LenderStudentLoan>> studentLoanList = rateStartService.getLenderStudentLoan(studentLoanType)
        studentLoanList.isPresent() ? studentLoanList.get() : null
    }
}
