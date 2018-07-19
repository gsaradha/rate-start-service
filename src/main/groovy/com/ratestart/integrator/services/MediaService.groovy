package com.ratestart.integrator.services

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.web.multipart.MultipartFile

@Service
@Slf4j
class MediaService {

    String getBase64Media(MultipartFile file) {
        log.info "Upload a new Media"
        Assert.isTrue(!Objects.isNull(file), "Media File is missing")

        String asB64 = Base64.getEncoder().encodeToString(file.getBytes())
        log.info ("Base64 Media - ${asB64}")
        asB64
    }
}
