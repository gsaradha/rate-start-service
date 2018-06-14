package com.ratestart.integrator.model

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class PlatformProperty {

    @Value('${RateStart.Media.Path}')
    String mediaPath

}
