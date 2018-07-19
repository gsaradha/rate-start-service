package com.ratestart.integrator.controllers

import com.ratestart.integrator.model.Error
import com.ratestart.integrator.model.FavoriteInfo
import com.ratestart.integrator.model.LenderInfo
import com.ratestart.integrator.model.LenderAutoEquity
import com.ratestart.integrator.model.LenderCreditCard
import com.ratestart.integrator.model.LenderHomeEquity
import com.ratestart.integrator.model.LenderMortgage
import com.ratestart.integrator.model.LenderStudentLoan
import com.ratestart.integrator.model.Category
import com.ratestart.integrator.model.UserAlert
import com.ratestart.integrator.model.UserInfo
import com.ratestart.integrator.services.MediaService
import com.ratestart.integrator.services.RateStartService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@CrossOrigin
@Log4j
@RestController
class RateStartController {

    @Autowired
    RateStartService rateStartService

    @Autowired
    MediaService mediaService

    @RequestMapping(value = "/users/{userId}/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<FavoriteInfo> fetchFavorites(@PathVariable("userId") Long userId) throws Exception {
        Optional<List<FavoriteInfo>> favoriteInfoList = rateStartService.fetchFavorites(userId)
        favoriteInfoList.isPresent() ? favoriteInfoList.get() : null
    }

    @RequestMapping(value = "/user/favorite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Object createFavorite(@Valid @RequestBody FavoriteInfo favoriteInfo, BindingResult bindingResult) throws Exception {
        log.info("FavoriteInfo Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid FavoriteInfo encountered - ${bindingResult}")
        }
        Optional<Object> favoriteInfoOptional = rateStartService.createFavorite(favoriteInfo)
        Object newFavoriteInfo = favoriteInfoOptional.get()
        log.info("Fetched FavoriteInfo: ${newFavoriteInfo}")
        newFavoriteInfo
    }

    @RequestMapping(value = "/favorites/{favoriteId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteFavorite(@PathVariable("favoriteId") Long favoriteId) throws Exception {
        log.info("Delete Favorite Requested")
        rateStartService.deleteFavorite(favoriteId)
    }

    @RequestMapping(value = "/user/alert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void saveUserAlert(@Valid @RequestBody UserAlert userAlert, BindingResult bindingResult) throws Exception {
        log.info("UserAlert Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid UserAlert encountered - ${bindingResult}")
        }
        rateStartService.saveUserAlert(userAlert)
    }

    @RequestMapping(value = "/lender/mortgage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
    Object loginLender(@Valid @RequestBody LenderInfo lender, BindingResult bindingResult) throws Exception {
        log.info("Lender Login Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid Lender encountered - ${bindingResult}")
        }
        Optional<Object> lenderInfo = rateStartService.loginLender(lender)
        log.info("Fetched LenderInfo: ${lenderInfo.get()}")
        lenderInfo.get()
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Object loginUser(@Valid @RequestBody UserInfo userInfo, BindingResult bindingResult) throws Exception {
        log.info("User Login Received")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid User encountered - ${bindingResult}")
        }
        Optional<Object> user = rateStartService.loginUser(userInfo)
        log.info("Fetched UserInfo: ${user.get()}")
        user.get()
    }

    @RequestMapping(value = "/lender/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Object signUpLender(@Valid @RequestBody LenderInfo lenderRequest) throws Exception {
        log.info("SignUp Received, preparing to process lender")
        //lenderRequest.base64Logo = mediaService.getBase64Media(file)

        Optional<Object> lenderInfo = rateStartService.signUpLender(lenderRequest)
        log.info("Fetched LenderInfo: ${lenderInfo.get()}")
        lenderInfo.get()
    }

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Object signUpUser(@Valid @RequestBody UserInfo userInfo, BindingResult bindingResult) throws Exception {
        log.info("SignUp Received, preparing to process user")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid User encountered - ${bindingResult}")
        }
        Optional<Object> user = rateStartService.signUpUser(userInfo)
        log.info("Fetched UserInfo: ${user.get()}")
        user.get()
    }

    @RequestMapping(value = "/user/deviceToken/alerts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<UserAlert> getUserAlerts(@Valid @RequestBody UserAlert userAlert) throws Exception {
        Optional<List<UserAlert>> userAlerts = rateStartService.fetchUserAlerts(userAlert.deviceToken)
        userAlerts.isPresent() ? userAlerts.get() : null
    }

    @RequestMapping(value = "/lenderMortgages/loanType/{loanTypeId}/loanOption/{loanOptionId}/loanTerm/{loanTermId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderMortgage> getLenderMortgages(@PathVariable("loanTypeId") Long loanTypeId,@PathVariable("loanOptionId") Long loanOptionId,@PathVariable("loanTermId") Long loanTermId) throws Exception {
        Optional<List<LenderMortgage>> mortgageList = rateStartService.getLenderMortgages(loanTypeId,loanOptionId,loanTermId)
        mortgageList.isPresent() ? mortgageList.get() : null
    }

    @RequestMapping(value = "/lenderHomeEquity/category/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<LenderHomeEquity> getLenderHomeEquity(@PathVariable("categoryId") Long categoryId) throws Exception {
        Optional<List<LenderHomeEquity>> homeEquityList = rateStartService.getLenderHomeEquity(categoryId)
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
