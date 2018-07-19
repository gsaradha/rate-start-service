package com.ratestart.integrator.controllers

import com.ratestart.integrator.model.Email
import com.ratestart.integrator.services.EmailService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@CrossOrigin
@Log4j
@RestController
class EmailController {

    @Autowired
    EmailService emailService

    @RequestMapping(value = "/lender/email", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void createFavorite(@Valid @RequestBody Email email) throws Exception {
        log.info("Email Notification Received")
        emailService.sendEmail(email)

    }

}
