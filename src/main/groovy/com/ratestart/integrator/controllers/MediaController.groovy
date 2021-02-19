package com.ratestart.integrator.controllers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.ratestart.integrator.model.Error
import com.ratestart.integrator.model.LenderInfo
import com.ratestart.integrator.services.MediaService
import com.ratestart.integrator.services.RateStartService
import com.ratestart.integrator.util.ExceptionUtils
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

import javax.validation.Valid
import java.nio.file.DirectoryNotEmptyException

@CrossOrigin
@Slf4j
@RestController
class MediaController {

    @Autowired
    MediaService mediaService

    @Autowired
    RateStartService rateStartService

    /*@RequestMapping(value = "/media/lender/signup", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Object signUpLender(@RequestPart("file") MultipartFile file, @Valid @RequestBody LenderInfo lender, BindingResult bindingResult) throws Exception {
        log.info("SignUp Received, preparing to process lender")
        if(bindingResult.hasErrors()) {
            new Error(errorMessage: "Invalid Lender encountered - ${bindingResult}")
        }
        lender.base64Logo = mediaService.getBase64Media(file)

        Optional<Object> lenderInfo = rateStartService.signUpLender(lender)
        log.info("Signed up LenderInfo: ${lenderInfo.get()}")
        lenderInfo.get()
    }*/

    @ExceptionHandler
    ResponseEntity<JsonNode> handleIOException(IOException exception) {
        log.error("IOException - ${exception.message}")
        ObjectNode objectNode = ExceptionUtils.getJsonError(exception.message)
        new ResponseEntity<JsonNode>(objectNode, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    ResponseEntity<JsonNode> handleDirectoryNotEmptyException(DirectoryNotEmptyException exception) {
        log.error("DirectoryNotEmptyException - ${exception.message}")
        ObjectNode objectNode = ExceptionUtils.getJsonError(exception.message)
        new ResponseEntity<JsonNode>(objectNode, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
