package com.ratestart.integrator.services

import com.ratestart.integrator.domain.LenderType
import com.ratestart.integrator.domain.LoanOption
import com.ratestart.integrator.domain.LoanTerm
import com.ratestart.integrator.domain.LoanType
import com.ratestart.integrator.model.Category
import com.ratestart.integrator.model.Error
import com.ratestart.integrator.model.FavoriteInfo
import com.ratestart.integrator.model.LenderAutoEquity
import com.ratestart.integrator.model.LenderCreditCard
import com.ratestart.integrator.model.LenderHomeEquity
import com.ratestart.integrator.model.LenderInfo
import com.ratestart.integrator.model.LenderMortgage
import com.ratestart.integrator.model.LenderStudentLoan
import com.ratestart.integrator.model.PlatformProperty
import com.ratestart.integrator.model.UserAlert
import com.ratestart.integrator.model.UserInfo
import com.ratestart.integrator.repo.AutoEquity
import com.ratestart.integrator.repo.AutoEquityRepository
import com.ratestart.integrator.repo.CreditCard
import com.ratestart.integrator.repo.CreditCardRepository
import com.ratestart.integrator.repo.Favorite
import com.ratestart.integrator.repo.FavoriteRepository
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
import com.ratestart.integrator.repo.SubscriptionAlert
import com.ratestart.integrator.repo.SubscriptionAlertRepository
import com.ratestart.integrator.repo.User
import com.ratestart.integrator.repo.UserRepository
import com.ratestart.integrator.util.DateUtils
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
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
    UserRepository userRepository

    @Autowired
    AutoEquityRepository autoEquityRepository

    @Autowired
    StudentLoanRepository studentLoanRepository

    @Autowired
    CreditCardRepository creditCardRepository

    @Autowired
    CategoryRepository tipsRepository

    @Autowired
    FavoriteRepository favoriteRepository

    @Autowired
    NotificationService notificationService

    @Autowired
    SubscriptionAlertRepository subscriptionAlertRepository

    @Autowired
    LenderEquityRepository lenderEquityRepository

    void approveLender(Long lenderId) {
        Lender lender = lenderEquityRepository.findOne(lenderId)
        lender.isVerified = true
        lenderEquityRepository.save(lender)
        log.info("Lender verified and approved")
    }

    void saveUserAlert(UserAlert userAlert) {
        Objects.requireNonNull(userAlert, "UserAlert is null!")
        SubscriptionAlert subscriptionAlert = new SubscriptionAlert(deviceToken: userAlert.deviceToken, idAlert: userAlert.alertId)

        Integer subscriptionAlertId = subscriptionAlertRepository.findExistingAlert(userAlert.alertId, userAlert.deviceToken)
        subscriptionAlert.idSubscriptionAlert = subscriptionAlertId

        if (subscriptionAlertId && !userAlert.isActive) {
            subscriptionAlertRepository.delete(subscriptionAlertId.toLong())

        } else if (!subscriptionAlertId && userAlert.isActive) {
            subscriptionAlertRepository.save(subscriptionAlert)

        }
        log.info("User Alert Saved")

    }

    void sendNotification(Long lenderId, LenderType lenderType, BigDecimal rate) {
        Lender lender = lenderRepository.findOne(lenderId)
        if (lender.isVerified) {
            log.info("Sending notification to all subscribers")
            notificationService.sendNotification(lenderType, rate)
        }
    }

    Optional<List<FavoriteInfo>> fetchFavorites(Long userId) {
        Objects.requireNonNull(userId, "userId is null!")
        List<Favorite> favoriteList = favoriteRepository.fetchFavorites(userId)
        List<FavoriteInfo> favoriteInfoList = convertFavoriteToFavoriteInfo(favoriteList)
        Optional.of(favoriteInfoList)
    }

    static List<FavoriteInfo> convertFavoriteToFavoriteInfo(List<Favorite> favoriteList) {
        List<FavoriteInfo> favoriteInfoList = []
        favoriteList.forEach { it ->
            favoriteInfoList.add(
                    new FavoriteInfo(
                            favoriteId: it.idFavorites,
                            userId: it.idUser,
                            tipId: it.idTip,
                            tip:  it.tip,
                            categoryId: it.idCategory
                    )
            )
        }
        favoriteInfoList
    }

    Optional<Object> createFavorite(FavoriteInfo favoriteInfo) {
        Objects.requireNonNull(favoriteInfo, "FavoriteInfo is null!")
        Favorite favorite = convertToFavorite(favoriteInfo)
        favorite = favoriteRepository.save(favorite)
        favoriteInfo.favoriteId = favorite.idFavorites
        Optional.of(favoriteInfo)
    }

    void deleteFavorite(Long favoriteId) {
        Objects.requireNonNull(favoriteId, "favoriteId is null!")
        favoriteRepository.deleteFavorite(favoriteId)
    }

    Favorite convertToFavorite(FavoriteInfo favoriteInfo) {
        new Favorite(
                idFavorites: favoriteInfo.favoriteId,
                idUser: favoriteInfo.userId,
                idTip: favoriteInfo.tipId,
                idCategory: favoriteInfo.categoryId
        )
    }

    Optional<Object> createLenderStudentLoan(LenderStudentLoan lenderStudentLoan) {

        Objects.requireNonNull(lenderStudentLoan, "LenderStudentLoan is null!")
        StudentLoan studentLoan = convertToStudentLoan(lenderStudentLoan)
        studentLoan = studentLoanRepository.save(studentLoan)

        sendNotification(studentLoan.lenderId, LenderType.STUDENT, studentLoan.apr)

        lenderStudentLoan.idStudentLoan = studentLoan.idStudentLoan
        Optional.of(lenderStudentLoan)
    }

    StudentLoan convertToStudentLoan(LenderStudentLoan lenderStudentLoan) {
        new StudentLoan(
                idStudentLoan: lenderStudentLoan.idStudentLoan,
                lenderId: lenderStudentLoan.lenderId,
                conditions: lenderStudentLoan.conditions,
                studentLoanType: lenderStudentLoan.studentLoanType,
                studentLoanCol: lenderStudentLoan.studentLoanCol,
                apr: lenderStudentLoan.apr,
                name: lenderStudentLoan.name,
                creditRange: lenderStudentLoan.creditRange,
                loanTerm: lenderStudentLoan.loanTerm
        )
    }

    Optional<Object> createLenderCreditCard(LenderCreditCard lenderCreditCard) {

        Objects.requireNonNull(lenderCreditCard, "LenderCreditCard is null!")
        CreditCard creditCard = convertToCreditCard(lenderCreditCard)
        creditCard = creditCardRepository.save(creditCard)

        sendNotification(creditCard.lenderId, LenderType.CREDIT, creditCard.purchase)

        lenderCreditCard.idCreditCard = creditCard.idCreditCard
        Optional.of(lenderCreditCard)
    }

    CreditCard convertToCreditCard(LenderCreditCard lenderCreditCard) {
        new CreditCard(
                idCreditCard: lenderCreditCard.idCreditCard,
                lenderId: lenderCreditCard.lenderId,
                name: lenderCreditCard.name,
                purchase: lenderCreditCard.purchase,
                balance: lenderCreditCard.balance,
                cashAdvance: lenderCreditCard.cashAdvance,
                introApr: lenderCreditCard.introApr,
                conditions:lenderCreditCard.conditions,
                cardType: lenderCreditCard.cardType,
                logoFilename: lenderCreditCard.logoFileName
        )
    }

    Optional<Object> createLenderAutoEquity(LenderAutoEquity lenderAutoEquity) {

        Objects.requireNonNull(lenderAutoEquity, "LenderAutoEquity is null!")
        AutoEquity autoEquity = convertLenderAutoEquityToAutoEquity(lenderAutoEquity)
        autoEquity = autoEquityRepository.save(autoEquity)

        sendNotification(autoEquity.idLender, LenderType.AUTO, autoEquity.apr)

        lenderAutoEquity.idAuto = autoEquity.idAuto
        Optional.of(lenderAutoEquity)
    }

    AutoEquity convertLenderAutoEquityToAutoEquity(LenderAutoEquity lenderAutoEquity) {
        new AutoEquity(
                idAuto: lenderAutoEquity.idAuto,
                rate:lenderAutoEquity.rate,
                apr:lenderAutoEquity.apr,
                credit:lenderAutoEquity.credit,
                creditRange: lenderAutoEquity.creditRange,
                conditions: lenderAutoEquity.conditions,
                date: DateUtils.getDate(lenderAutoEquity.date),
                logoFileName: lenderAutoEquity.logoFileName,
                idLender: lenderAutoEquity.lenderId,
                loanOption: lenderAutoEquity.loanOption,
                loanTerm: lenderAutoEquity.loanTerm,
                productCondition: lenderAutoEquity.productCondition
        )
    }

    Optional<Object> createLenderHomeEquity(LenderHomeEquity lenderHomeEquity) {

        Objects.requireNonNull(lenderHomeEquity, "LenderHomeEquity is null!")
        HomeEquity homeEquity = convertLenderHomeEquityToHomeEquity(lenderHomeEquity)
        homeEquity = homeEquityRepository.save(homeEquity)

        sendNotification(homeEquity.lenderId, LenderType.HOME, homeEquity.rate)

        lenderHomeEquity.idHomeEquity = homeEquity.idHomeEquity
        Optional.of(lenderHomeEquity)
    }

    HomeEquity convertLenderHomeEquityToHomeEquity(LenderHomeEquity lenderHomeEquity) {
                    new HomeEquity(
                            idHomeEquity: lenderHomeEquity.idHomeEquity,
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
                            date: DateUtils.getDate(lenderHomeEquity.date),
                            logoFilename: lenderHomeEquity.logoFileName,
                            conditions: lenderHomeEquity.conditions,
                            category:lenderHomeEquity.category,
                            phone: lenderHomeEquity.phone
                    )
    }

    Optional<Object> createLenderMortgage(LenderMortgage lenderMortgage) {

        Objects.requireNonNull(lenderMortgage, "LenderMortgage is null!")
        Mortgage mortgage = convertMortgageInfoToMortgage(lenderMortgage)
        mortgage = mortgageRepository.save(mortgage)

        sendNotification(mortgage.idLender, LenderType.MORTGAGE, mortgage.apr)

        lenderMortgage.mortgageId = mortgage.idMortgage
        Optional.of(lenderMortgage)
    }

    Mortgage convertMortgageInfoToMortgage(LenderMortgage lenderMortgage) {
        new Mortgage(
                 idMortgage: lenderMortgage.mortgageId,
                 name: lenderMortgage.name,
                 idLender: lenderMortgage.lenderId,
                 loanType: LoanType.getLoanType(lenderMortgage.loanType),
                 loanOption: LoanOption.getLoanOption(lenderMortgage.loanOption),
                 loanTerm: LoanTerm.getLoanTerm(lenderMortgage.loanTerm),
                 fees: lenderMortgage.fees,
                 points: lenderMortgage.points,
                 apr: lenderMortgage.apr,
                 ratePeriod: lenderMortgage.ratePeriod,
                 monthlyPay: lenderMortgage.monthlyPay,
                 date: DateUtils.getDate(lenderMortgage.date),
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

    Optional<Object> loginUser(UserInfo userInfo) {

        Objects.requireNonNull(userInfo, "UserInfo is null!")
        Objects.requireNonNull(userInfo.email, "Email cannot be null!")
        Objects.requireNonNull(userInfo.password, "Password cannot be null!")

        User user = userRepository.fetchUser(userInfo.email, userInfo.password)
        if (!user) {
            Optional.of(new Error(errorMessage: "Invalid User email / password"))
        } else {
            convertUserToUserInfo(user)
        }
    }

    Optional<Object> signUpLender(LenderInfo lenderInfo) {

        Objects.requireNonNull(lenderInfo, "LenderInfo is null!")
        Objects.requireNonNull(lenderInfo.userName, "UserName cannot be null!")
        Objects.requireNonNull(lenderInfo.password, "Password cannot be null!")

        Lender lender = lenderRepository.fetchLender(lenderInfo.userName, lenderInfo.password)
        if (lender) {
            Optional.of(new Error(errorMessage: "Lender already exists"))
        } else {
            Lender newLender = convertLenderInfoToLender(lenderInfo)
            newLender = lenderRepository.save(newLender)
            lenderInfo.idLender = newLender.idLender
            Optional.of(lenderInfo)
        }
    }

    Optional<Object> signUpUser(UserInfo userInfo) {

        Objects.requireNonNull(userInfo, "UserInfo is null!")
        Objects.requireNonNull(userInfo.email, "User email cannot be null!")
        Objects.requireNonNull(userInfo.password, "Password cannot be null!")

        User user = userRepository.fetchExistingUser(userInfo.email?.trim())
        if (user) {
            Optional.of(new Error(errorMessage: "User already exists"))
        } else {
            User newUser = convertUserInfoToUser(userInfo)
            newUser = userRepository.save(newUser)
            userInfo.idUser = newUser.idUser
            Optional.of(userInfo)
        }
    }

    Optional<Object> signUpUserForgotPassword(UserInfo userInfo) {

        Objects.requireNonNull(userInfo, "UserInfo is null!")
        Objects.requireNonNull(userInfo.email, "User email cannot be null!")
        Objects.requireNonNull(userInfo.password, "Password cannot be null!")

        User user = userRepository.fetchExistingUser(userInfo.email?.trim())
        if (user) {
            User newUser = convertUserInfoToUser(userInfo)
            newUser = userRepository.save(newUser)
            userInfo.idUser = newUser.idUser
            Optional.of(userInfo)
        } else {
            Optional.of(new Error(errorMessage: "User email does not exists"))
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
                isVerified: lenderInfo.isVerified,
                logoFilename: lenderInfo.logoFileName,
                base64Logo: lenderInfo.base64Logo)
    }

    User convertUserInfoToUser(UserInfo userInfo) {
        new User(
                name: userInfo.name,
                email: userInfo.email,
                password: userInfo.password,
                hasSubscribed: userInfo.hasSubscribed
               )
    }
    Optional<UserInfo> convertUserToUserInfo(User user) {
        UserInfo userInfo =
                new UserInfo(
                        idUser: user.idUser,
                        name: user.name,
                        email: user.email,
                        hasSubscribed: user.hasSubscribed,
                       )
        Optional.of(userInfo)
    }

    Optional<LenderInfo> convertLenderToLenderInfo(Lender lender) {
        LenderInfo lenderInfo =
                new LenderInfo(
                    idLender: lender.idLender,
                    name: lender.name,
                    email: lender.email,
                    phone: lender.phone,
                    isMortgageLender: lender.isMortgageLender,
                    nmlsId: lender.nmlsId,
                    stateLicense: lender.stateLicense,
                        isVerified: lender.isVerified,
                    logoFileName: lender.logoFilename,
                        base64Logo: lender.base64Logo
                )
        Optional.of(lenderInfo)
    }

    Optional<List<UserAlert>> fetchUserAlerts(String deviceToken) {
        List<SubscriptionAlert> subscriptionAlertList = subscriptionAlertRepository.fetchAlertsByDeviceToken(deviceToken)
        List<UserAlert> userAlertList = []
        subscriptionAlertList.forEach { it ->
            userAlertList.add(new UserAlert(alertId: it.idAlert))
        }
        Optional.of(userAlertList)
    }

    Optional<List<LenderMortgage>> getLenderMortgages(Long loanTypeId,Long loanOptionId,Long loanTermId) {
        List<Mortgage> mortgageList = mortgageRepository.fetchMortgages(loanTypeId,loanOptionId,loanTermId)
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
                            loanTerm:it.loanTerm,
                            fees: it.fees,
                            points: it.points,
                            apr: it.apr,
                            ratePeriod: it.ratePeriod,
                            monthlyPay: it.monthlyPay,
                            date: DateUtils.getDateString(it.date),
                            nmlsId: it.nmlsId,
                            logoFileName: it.logoFileName,
                            base64Logo: it.base64Logo
                    )
            )
        }
        lenderMortgageList
    }

    Optional<List<LenderHomeEquity>> getLenderHomeEquity(Long categoryId) {
        List<HomeEquity> homeEquityList = homeEquityRepository.fetchHomeEquity(categoryId)
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
                            date: DateUtils.getDateString(it.date),
                            logoFileName: it.logoFilename,
                            conditions:it.conditions,
                            category: it.category,
                            phone: it.phone,
                            base64Logo: it.base64Logo
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
                            loanTerm: it.loanTerm,
                            loanOption: it.loanOption,
                            creditRange: it.creditRange,
                            conditions: it.conditions,
                            date: DateUtils.getDateString(it.date),
                            logoFileName: it.logoFileName,
                            base64Logo: it.base64Logo,
                            productCondition:it.productCondition
                    )
            )
        }
        lenderAutoEquities
    }

    Optional<List<LenderCreditCard>> getLenderCreditCard(Long cardTypeId) {
        List<CreditCard> creditCardList = creditCardRepository.fetchCreditCard(cardTypeId)
        List<LenderCreditCard> lenderCreditCardList = convertToLenderCreditCard(creditCardList)
        Optional.ofNullable(lenderCreditCardList)
    }

    List<LenderCreditCard> convertToLenderCreditCard(List<CreditCard> creditCardList) {
        List<LenderCreditCard> lenderCreditCards = []
        creditCardList.forEach{it ->
            lenderCreditCards.add(
                    new LenderCreditCard(
                            idCreditCard: it.idCreditCard,
                            lenderId: it.lenderId,
                            name: it.name,
                            purchase: it.purchase,
                            balance: it.balance,
                            cashAdvance: it.cashAdvance,
                            introApr: it.introApr,
                            date: DateUtils.getDateString(it.date),
                            conditions:it.conditions,
                            cardType: it.cardType,
                            logoFileName: it.logoFilename,
                            base64Logo: it.base64Logo
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
                            logoFilename: it.logoFilename
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
                            studentLoanType: it.studentLoanType,
                            studentLoanCol:it.studentLoanCol,
                            apr:it.apr,
                            creditRange: it.creditRange,
                            loanTerm:it.loanTerm,
                            logoFileName: it.logoFilename,
                            base64Logo: it.base64Logo,
                            phone: it.phone
                    )
            )
        }
        lenderStudentLoanList
    }

}
