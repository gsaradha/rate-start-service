package com.ratestart.integrator.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.validation.annotation.Validated

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@Validated
class Email {

    String from
    String to
    String bcc
    String subject
    String message
    String lenderId

}
