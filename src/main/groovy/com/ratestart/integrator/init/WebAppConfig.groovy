package com.ratestart.integrator.init

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@ComponentScan(["com.ratestart.integrator.controllers"])
@EnableWebMvc
@EnableRetry
class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment environment

    @Autowired
    JdbcTemplate jdbcTemplate

    @Bean
    JavaMailSender javaMailSender() {

        Properties mailProperties = [
                "mail.transport.protocol": environment.getProperty("MAIL.TRANSPORT.PROTOCOL"),
                "mail.smtp.auth": environment.getProperty("MAIL.SMTP.AUTH"),
                "mail.smtp.starttls.enable": environment.getProperty("MAIL.SMTP.STARTTLS.ENABLE"),
                "mail.smtps.ssl.checkserveridentity": environment.getProperty("MAIL.SMTPS.SSL.CHECKSERVERIDENTITY"),
                "mail.smtps.ssl.trust": environment.getProperty("MAIL.SMTPS.SSL.TRUST"),
                "mail.smtp.ssl.enable": environment.getProperty("MAIL.SMTP.SSL.ENABLE"),
                "mail.debug": environment.getProperty("MAIL.DEBUG")
        ]

        new JavaMailSenderImpl(
                host: environment.getProperty("EMAIL.HOST"),
                port: environment.getProperty("EMAIL.PORT")?.toInteger(),
                username: environment.getProperty("EMAIL.USERNAME"),
                password: environment.getProperty("EMAIL.PASSWORD"),
                javaMailProperties: mailProperties
        )
    }

}
