package com.ratestart.integrator.util

import com.ratestart.integrator.model.PlatformProperty
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Slf4j
@Component
class LocationValidator {

    @Autowired
    PlatformProperty platformProperty

    /* void validate(DeleteMediaRequest request) {
        log.info("Validating location: ${request.location}")
        Assert.isTrue(StringUtils.isNotBlank(request.location), "Location cannot be empty")
        Assert.isTrue(request.location.startsWith("/media"), "Location: ${request.location} does not start with the proper media path")

        Path filePath = Paths.get(platformProperty.commonPath, request.location)
        log.info("Constructed file path: ${filePath.toString()}")
        Assert.isTrue(Files.exists(filePath), "File does not exist at given location")
    } */
}
