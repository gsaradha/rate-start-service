package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserInfo {
    Long idUser
    String name
    String email
    String password
    Boolean hasSubscribed
}
