package com.ratestart.integrator.init

import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.integration.config.EnableMessageHistory
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver

@Configuration
@Import(WebAppConfig)
@ComponentScan(value = "com.ratestart.integrator", excludeFilters = [@ComponentScan.Filter(type = FilterType.REGEX, pattern = ["com.ratestart.integrator.init.*"])])
@EnableMessageHistory
@PropertySource(["classpath:rate-start.properties", "classpath:db.properties"])
@ImportResource("classpath:/WEB-INF/spring/integration/applicationContext.xml")
@Slf4j
class AppConfig {

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }
}
