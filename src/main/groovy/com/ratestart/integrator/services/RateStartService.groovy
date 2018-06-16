package com.ratestart.integrator.services

import com.newrelic.api.agent.NewRelic
import com.newrelic.api.agent.Trace
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.model.AdPixel
import com.ratestart.integrator.model.AdPixelResponse
import com.ratestart.integrator.model.Category
import com.ratestart.integrator.model.Error
import com.ratestart.integrator.model.LenderAutoEquity
import com.ratestart.integrator.model.LenderCreditCard
import com.ratestart.integrator.model.LenderHomeEquity
import com.ratestart.integrator.model.LenderInfo
import com.ratestart.integrator.model.LenderMortgage
import com.ratestart.integrator.model.LenderStudentLoan
import com.ratestart.integrator.model.MortgageInfo
import com.ratestart.integrator.model.PlatformProperty
import com.ratestart.integrator.repo.AutoEquity
import com.ratestart.integrator.repo.AutoEquityRepository
import com.ratestart.integrator.repo.CreditCard
import com.ratestart.integrator.repo.CreditCardRepository
import com.ratestart.integrator.repo.HomeEquity
import com.ratestart.integrator.repo.HomeEquityRepository
import com.ratestart.integrator.repo.Lender
import com.ratestart.integrator.repo.LenderEquityRepository
import com.ratestart.integrator.repo.Mortgage
import com.ratestart.integrator.repo.MortgageRepository
import com.ratestart.integrator.repo.StudentLoan
import com.ratestart.integrator.repo.StudentLoanRepository
import com.ratestart.integrator.repo.CategoryTips
import com.ratestart.integrator.repo.CategoryRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Slf4j
@Service
class RateStartService {

    @Autowired
    PlatformProperty platformProperty

    @Autowired
    MortgageRepository mortgageRepository

    @Autowired
    HomeEquityRepository homeEquityRepository

    @Autowired
    LenderEquityRepository lenderRepository

    @Autowired
    AutoEquityRepository autoEquityRepository

    @Autowired
    StudentLoanRepository studentLoanRepository

    @Autowired
    CreditCardRepository creditCardRepository

    @Autowired
    CategoryRepository tipsRepository


    Optional<Object> createLenderHomeEquity(LenderHomeEquity lenderHomeEquity) {

        Objects.requireNonNull(lenderHomeEquity, "LenderHomeEquity is null!")
        HomeEquity homeEquity = convertLenderHomeEquityToHomeEquity(lenderHomeEquity)
        homeEquity = homeEquityRepository.save(homeEquity)
        homeEquity.idHomeEquity = homeEquity.idHomeEquity
        Optional.of(homeEquity)
    }

    HomeEquity convertLenderHomeEquityToHomeEquity(LenderHomeEquity lenderHomeEquity) {
                    new HomeEquity(
                            lenderId:lenderHomeEquity.lenderId,
                            loanType: lenderHomeEquity.loanType,
                            name: lenderHomeEquity.name,
                            rate: lenderHomeEquity.rate,
                            credit:lenderHomeEquity.credit,
                            amount:lenderHomeEquity.amount,
                            fees:lenderHomeEquity.fees,
                            afterIntroRate:lenderHomeEquity.afterIntroRate,
                            maxLtv:lenderHomeEquity.maxLtv,
                            stateLicense:lenderHomeEquity.stateLicense,
                            requiredDraw:lenderHomeEquity.requiredDraw,
                            date: lenderHomeEquity.date,
                            logoFilename: lenderHomeEquity.logoFileName,
                            conditions: lenderHomeEquity.conditions
                    )
    }

    Optional<Object> createLenderMortgage(LenderMortgage lenderMortgage) {

        Objects.requireNonNull(lenderMortgage, "LenderMortgage is null!")
        Mortgage mortgage = convertMortgageInfoToMortgage(lenderMortgage)
        mortgage = mortgageRepository.save(mortgage)
        lenderMortgage.mortgageId = mortgage.idMortgage
        Optional.of(lenderMortgage)
    }

    Mortgage convertMortgageInfoToMortgage(LenderMortgage lenderMortgage) {
        new Mortgage(
                 name: lenderMortgage.name,
                 idLender: lenderMortgage.lenderId,
                 loanType: LoanType.getLoanType(lenderMortgage.loanType),
                 loanOption: LoanOption.getLoanOption(lenderMortgage.loanOption),
                 fees: lenderMortgage.fees,
                 points: lenderMortgage.points,
                 apr: lenderMortgage.apr,
                 ratePeriod: lenderMortgage.ratePeriod,
                 monthlyPay: lenderMortgage.monthlyPay,
                 date: lenderMortgage.date,
                 nmlsId: lenderMortgage.nmlsId,
                 logoFileName: lenderMortgage.logoFileName)
    }




    Optional<Object> loginLender(LenderInfo lenderInfo) {

        Objects.requireNonNull(lenderInfo, "LenderInfo is null!")
        Objects.requireNonNull(lenderInfo.userName, "UserName cannot be null!")
        Objects.requireNonNull(lenderInfo.password, "Password cannot be null!")

        Lender lender = lenderRepository.fetchLender(lenderInfo.userName, lenderInfo.password)
        if (!lender) {
            Optional.of(new Error(errorMessage: "Invalid Username or password"))
        } else {
            convertLenderToLenderInfo(lender)
        }
    }



    Lender convertLenderInfoToLender(LenderInfo lenderInfo) {
        new Lender(
                userName: lenderInfo.userName,
                password: lenderInfo.password,
                name: lenderInfo.name,
                email: lenderInfo.email,
                phone: lenderInfo.phone,
                isMortgageLender: lenderInfo.isMortgageLender,
                nmlsId: lenderInfo.nmlsId,
                stateLicense: lenderInfo.stateLicense,
                logoFilename: lenderInfo.logoFileName)
    }

    Optional<LenderInfo> convertLenderToLenderInfo(Lender lender) {
        LenderInfo lenderInfo =
                new LenderInfo(idLender: lender.idLender,
                name: lender.name,
                email: lender.email,
                phone: lender.phone,
                isMortgageLender: lender.isMortgageLender,
                nmlsId: lender.nmlsId,
                stateLicense: lender.stateLicense,
                logoFileName: lender.logoFilename)
        Optional.of(lenderInfo)
    }

    Optional<List<LenderMortgage>> getLenderMortgages(Long loanTypeId,Long loanOptionId) {
        List<Mortgage> mortgageList = mortgageRepository.fetchMortgages(loanTypeId,loanOptionId)
        List<LenderMortgage> lenderMortgage = domainToModel(mortgageList)
        Optional.ofNullable(lenderMortgage)
    }

    List<LenderMortgage> domainToModel(List<Mortgage> mortgageList) {
        List<LenderMortgage> lenderMortgageList = []
        mortgageList.forEach{it ->
            lenderMortgageList.add(
                    new LenderMortgage(
                            mortgageId: it.idMortgage,
                            name: it.name,
                            loanOption: it.loanOption,
                            loanType: it.loanType,
                            fees: it.fees,
                            points: it.points,
                            apr: it.apr,
                            ratePeriod: it.ratePeriod,
                            monthlyPay: it.monthlyPay,
                            date: it.date,
                            nmlsId: it.nmlsId,
                            stateLicense: it.stateLicense,
                            logoFileName: it.logoFileName
                    )
            )
        }
        lenderMortgageList
    }

    Optional<List<LenderHomeEquity>> getLenderHomeEquity(Long loanTypeId) {
        List<HomeEquity> homeEquityList = homeEquityRepository.fetchHomeEquity(loanTypeId)
        List<LenderHomeEquity> lenderHomeEquity = equityToHomeEquity(homeEquityList)
        Optional.ofNullable(lenderHomeEquity)
    }

    List<LenderHomeEquity> equityToHomeEquity(List<HomeEquity> homeEquityList) {
        List<LenderHomeEquity> lenderHomeEquityList = []
        homeEquityList.forEach{it ->
            lenderHomeEquityList.add(
                    new LenderHomeEquity(
                            idHomeEquity: it.idHomeEquity,
                            lenderId:it.lenderId,
                            loanType: it.loanType,
                            name: it.name,
                            rate: it.rate,
                            credit:it.credit,
                            amount:it.amount,
                            fees:it.fees,
                            afterIntroRate:it.afterIntroRate,
                            maxLtv:it.maxLtv,
                            stateLicense:it.stateLicense,
                            requiredDraw:it.requiredDraw,
                            date: it.date,
                            logoFileName: it.logoFilename,
                            conditions:it.conditions
                    )
            )
        }
        lenderHomeEquityList
    }

    Optional<List<LenderAutoEquity>> getAutoEquity(Long loanOptionId, Long productConditionId,Long loanTerm) {
        List<AutoEquity> autoEquityList = autoEquityRepository.fetchAutoEquity(loanOptionId, productConditionId,loanTerm)
        List<LenderAutoEquity> lenderAutoEquityList = equityToAutoEquity(autoEquityList)
        Optional.ofNullable(lenderAutoEquityList)
    }

    List<LenderAutoEquity> equityToAutoEquity(List<AutoEquity> autoEquityList) {
        List<LenderAutoEquity> lenderAutoEquities = []
        autoEquityList.forEach{it ->
            lenderAutoEquities.add(
                    new LenderAutoEquity(
                            idAuto: it.idAuto,
                            rate:it.rate,
                            apr:it.apr,
                            credit:it.credit,
                            creditRange: it.creditRange,
                            conditions: it.conditions,
                            date: it.date,
                            logoFileName: it.logoFileName,
                            productCondition:it.productCondition
                    )
            )
        }
        lenderAutoEquities
    }


    Optional<List<LenderCreditCard>> getLenderCreditCard(Long cardTypeId) {
        List<CreditCard> creditCardList = creditCardRepository.fetchCreditCard(cardTypeId)
        List<LenderCreditCard> lenderCreditCardList = equityToCreditCard(creditCardList)
        Optional.ofNullable(lenderCreditCardList)
    }

    List<LenderCreditCard> equityToCreditCard(List<CreditCard> creditCardList) {
        List<LenderCreditCard> lenderCreditCards = []
        creditCardList.forEach{it ->
            lenderCreditCards.add(
                    new LenderCreditCard(
                            idCreditCard: it.idCreditCard,
                            lenderId: it.lenderId,
                            purchase: it.purchase,
                            balance: it.balance,
                            cashAdvance: it.cashAdvance,
                            introApr: it.introApr,
                            date:it.date,
                            conditions:it.conditions,
                            stateLicense: it.stateLicense,
                            cardType: it.cardType,
                            logoFileName: it.logoFilename
                    )
            )
        }
        lenderCreditCards
    }

    Optional<Category> getCategoryTips(Integer idCategory) {
        List<CategoryTips> categoryTips = tipsRepository.fetchCategoryTips(idCategory)
        Category category = convertToCategoryTips(categoryTips)
        Optional.ofNullable(category)
    }

    Category convertToCategoryTips(List<CategoryTips> categoryTips) {
        Category category = new Category()
        categoryTips.forEach{it ->
            category.categoryId = it.categoryId
            category.categoryName = it.description
            category.categoryImage = it.fileName
            category.icon = it.iconName
            Category.Tip tip = new Category.Tip()
            tip.id = it.idTips
            tip.text = it.tip
            category.tips.add(tip)
        }
        category
    }




    Optional<List<Lender>> getLender(String username,String password) {
        List<Lender> lenderEquityList = lenderRepository.fetchLender(username,password)
        List<Lender> lender = lenderDetails(lenderEquityList)
        Optional.ofNullable(lender)
    }



    List<Lender> lenderDetails(List<Lender> lenderList) {
        List<Lender> lenderEquityList = []
        lenderList.forEach{it ->
            lenderEquityList.add(
                    new Lender(
                            idLender: it.idLender,
                            name: it.name,
                            email : it.email,
                            isMortgageLender: it.isMortgageLender,
                            nmlsId: it.nmlsId,
                            stateLicense: it.stateLicense,
                            phone: it.phone,
                            logoFileName: it.logoFilename
                    )
            )
        }
        lenderEquityList
    }

    Optional<List<LenderStudentLoan>> getLenderStudentLoan(Long loanTypeId) {
        List<StudentLoan> studentLoanList = studentLoanRepository.fetchStudentLoan(loanTypeId)
        List<LenderStudentLoan> lenderStudentLoan = convertToLenderStudentLoan(studentLoanList)
        Optional.ofNullable(lenderStudentLoan)
    }

    List<LenderStudentLoan> convertToLenderStudentLoan(List<StudentLoan> studentLoanList) {
        List<LenderStudentLoan> lenderStudentLoanList = []
        studentLoanList.forEach{it ->
            lenderStudentLoanList.add(
                    new LenderStudentLoan(
                            idStudentLoan: it.idStudentLoan,
                            lenderId:it.lenderId,
                            conditions: it.conditions,
                            studentLoanCol:it.studentLoanCol,
                            apr:it.apr,
                            stateLicense: it.stateLicense,
                            logoFileName: it.logoFileName,
                            studentLoanDesc: it.studentLoanDesc

                    )
            )
        }
        lenderStudentLoanList
    }



    @Trace(dispatcher = true)
    Optional<AdPixelResponse> getById(String adPixelId, String accessToken) {

        NewRelic.incrementCounter("Custom/Service/AdPixel/getById/initiated")

        Objects.requireNonNull(adPixelId, "adPixelId may not be null!")

        def endPointUrl = enrichApiUrlForGetById(adPixelId, accessToken)

        log.info("fetch AdPixel information for adPixelId ${adPixelId}")
        AdPixel adPixel = (AdPixel)apiCaller.call(
                endPointUrl,
                new ParameterizedTypeReference<AdPixel>() {},
                HttpMethod.GET)

        NewRelic.incrementCounter("Custom/Service/AdPixel/getById/succeeded")
        Optional.ofNullable(new AdPixelResponse(data: [adPixel]))
    }


    def enrichApiUrlForGet(String accountId, String accessToken) {
        platformProperty.apiUrl + "/act_" + accountId + "/adspixels?access_token=" + accessToken + "&fields=id,name,code"
    }

    def enrichApiUrlForCreate(String accountId, String accessToken) {
        platformProperty.apiUrl + "/act_" + accountId + "/adspixels?access_token=" + accessToken
    }

    def enrichApiUrlForUpdate(String adPixelId, String accessToken) {
        platformProperty.apiUrl + "/" + adPixelId + "?access_token=" + accessToken
    }

    def enrichApiUrlForGetById(String adPixelId, String accessToken) {
        platformProperty.apiUrl + "/" + adPixelId + "?access_token=" + accessToken + "&fields=id,name,code"
    }
}
