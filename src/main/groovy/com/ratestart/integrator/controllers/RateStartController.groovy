package com.ratestart.integrator.controllers

import com.ratestart.integrator.model.Error
import com.ratestart.integrator.model.LenderInfo
import com.ratestart.integrator.model.LenderAutoEquity
import com.ratestart.integrator.model.LenderCreditCard
import com.ratestart.integrator.model.LenderHomeEquity
import com.ratestart.integrator.model.LenderMortgage
import com.ratestart.integrator.model.LenderStudentLoan
import com.ratestart.integrator.model.Category
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

    @RequestMapping(value = "/lender/mortgage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object createLenderMortgage(@Valid @RequestBody LenderMortgage lenderMortgage, BindingResult bindingResult) throws Exception {
        log.info("LenderMortgage Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid LenderMortgage encountered - ${bindingResult}")
        }
        Optional<Object> lenderMortgageOptional = rateStartService.createLenderMortgage(lenderMortgage)
        Object lMortgage = lenderMortgageOptional.get()
        log.info("Fetched LenderMortgage: ${lMortgage}")
        lMortgage
    }

    @RequestMapping(value = "/lender/homeEquity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object createLenderHomeEquity(@Valid @RequestBody LenderHomeEquity lenderHomeEquity, BindingResult bindingResult) throws Exception {
        log.info("LenderHomeEquity Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid LenderHomeEquity encountered - ${bindingResult}")
        }
        Optional<Object> lenderHomeEquityOptional = rateStartService.createLenderHomeEquity(lenderHomeEquity)
        Object hEquity = lenderHomeEquityOptional.get()
        log.info("Fetched LenderHomeEquity: ${hEquity}")
        hEquity
    }

    @RequestMapping(value = "/lender/autoEquity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object createLenderAutoEquity(@Valid @RequestBody LenderAutoEquity lenderAutoEquity, BindingResult bindingResult) throws Exception {
        log.info("LenderAutoEquity Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid LenderAutoEquity encountered - ${bindingResult}")
        }
        Optional<Object> lenderAutoEquityOptional = rateStartService.createLenderAutoEquity(lenderAutoEquity)
        Object aEquity = lenderAutoEquityOptional.get()
        log.info("Fetched LenderAutoEquity: ${aEquity}")
        aEquity
    }

    @RequestMapping(value = "/lender/creditCard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object createLenderCreditCard(@Valid @RequestBody LenderCreditCard lenderCreditCard, BindingResult bindingResult) throws Exception {
        log.info("LenderCreditCard Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid LenderCreditCard encountered - ${bindingResult}")
        }
        Optional<Object> lenderCreditCardOptional = rateStartService.createLenderCreditCard(lenderCreditCard)
        Object creditCard = lenderCreditCardOptional.get()
        log.info("Fetched LenderCreditCard: ${creditCard}")
        creditCard
    }

    @RequestMapping(value = "/lender/studentLoan", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object createLenderStudentLoan(@Valid @RequestBody LenderStudentLoan lenderStudentLoan, BindingResult bindingResult) throws Exception {
        log.info("LenderStudentLoan Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid LenderStudentLoan encountered - ${bindingResult}")
        }
        Optional<Object> lenderStudentLoanOptional = rateStartService.createLenderStudentLoan(lenderStudentLoan)
        Object studentLoan = lenderStudentLoanOptional.get()
        log.info("Fetched LenderStudentLoan: ${studentLoan}")
        studentLoan
    }

    @RequestMapping(value = "/lender/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object loginLender(@Valid @RequestBody LenderInfo lender, BindingResult bindingResult) throws Exception {
        log.info("Login Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid Lender encountered - ${bindingResult}")
        }
        Optional<Object> lenderInfo = rateStartService.loginLender(lender)
        log.info("Fetched LenderInfo: ${lenderInfo.get()}")
        lenderInfo.get()
    }

    @RequestMapping(value = "/lender/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Object signUpLender(@Valid @RequestBody LenderInfo lender, BindingResult bindingResult) throws Exception {
        log.info("SignUp Received, preparing to process lender")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid Lender encountered - ${bindingResult}")
        }
        Optional<Object> lenderInfo = rateStartService.signUpLender(lender)
        log.info("Fetched LenderInfo: ${lenderInfo.get()}")
        lenderInfo.get()
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

    @RequestMapping(value = "/lenderStudentLoan/studentLoanType/{studentLoanType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderStudentLoan> getLenderStudentLoan(@PathVariable("studentLoanType") Long studentLoanType) throws Exception {
        Optional<List<LenderStudentLoan>> studentLoanList = rateStartService.getLenderStudentLoan(studentLoanType)
        studentLoanList.isPresent() ? studentLoanList.get() : null
    }

    @RequestMapping(value = "/lenderCreditCard/creditCardType/{cardTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderCreditCard> getLenderCreditCard(@PathVariable("cardTypeId") Long cardTypeId) throws Exception {
        Optional<List<LenderCreditCard>> creditCardList = rateStartService.getLenderCreditCard(cardTypeId)
        creditCardList.isPresent() ? creditCardList.get() : null
    }

    @RequestMapping(value = "/category/categoryId/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Category getCategory(@PathVariable("categoryId") Integer categoryId) throws Exception {
        Optional<Category> categoryOptional = rateStartService.getCategoryTips(categoryId)
        categoryOptional.get()
    }

}
