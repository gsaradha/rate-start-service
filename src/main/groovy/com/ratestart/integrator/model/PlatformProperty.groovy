package com.ratestart.integrator.model

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class PlatformProperty {

    @Value('${RateStart.Media.Path}')
    String mediaPath

    @Value('${RateStart.Server.Key}')
    String serverKey

    @Value('${RateStart.Server.Url}')
    String serverUrl

    @Value('${RateStart.Ios.Publish.Key}')
    String iosPublishKey

    @Value('${RateStart.Ios.Secret.Key}')
    String iosSecretKey

    @Value('${RateStart.Ios.Server.Url}')
    String iosServerUrl

}
