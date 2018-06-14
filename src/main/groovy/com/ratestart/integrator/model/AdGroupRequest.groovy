package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class AdGroupRequest {

    String name

    @JsonProperty("adset_id")
    String adSetId

    @JsonProperty("status")
    String status

    //enables Api response with all fields in case of true
    Boolean redownload = Boolean.FALSE

    @JsonProperty("tracking_specs")
    Map<String, String> trackingSpecs

}
