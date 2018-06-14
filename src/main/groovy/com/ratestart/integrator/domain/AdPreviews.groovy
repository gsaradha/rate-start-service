package com.ratestart.integrator.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames = true, includePackage = false)
@JsonIgnoreProperties(ignoreUnknown = true)
class AdPreviews {

    List<String> previews
}
