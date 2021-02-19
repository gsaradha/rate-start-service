package com.ratestart.integrator.builders

import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

import java.lang.annotation.Target

@Builder(builderStrategy = ExternalStrategy, forClass = Target, prefix = "with")
class TargetBuilder {

    TargetBuilder using() {
        this
    }
}
