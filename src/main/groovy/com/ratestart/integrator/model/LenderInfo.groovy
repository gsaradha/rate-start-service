package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonRootName
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
class LenderInfo {
    Long idLender
    String userName
    String password
    String name
    String email
    Boolean isMortgageLender
    String nmlsId
    String stateLicense
    String phone
    Boolean isVerified
    String logoFileName
    String base64Logo
}
