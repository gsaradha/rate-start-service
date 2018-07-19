package com.ratestart.integrator.services

import com.ratestart.integrator.model.Email
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Slf4j
@Service
class EmailService {

    @Autowired
    JavaMailSender javaMailSender

    void sendEmail(Email email) {

        log.info "*** Send Email ***"
        MimeMessage message = javaMailSender.createMimeMessage()
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8")

        helper.setFrom(email.from)
        helper.setTo((parseReceiverAddress(email.to)))
        helper.setBcc(email.bcc)
        helper.setSubject(email.subject)
        helper.setText(email.message, true)

        log.info "Email Content: ${email}"

        try {

            javaMailSender.send(message);
            log.info "*** Email sent successfully ***"

        } catch (Exception messageExp) {
            log.error "*** Email failed with an Exception Message ${messageExp.getMessage()} ***"
        }

    }

    String[] parseReceiverAddress(String toEmail) {

        log.info "To Email address/es are ${toEmail}"
        List<String> toEmails = toEmail.tokenize(',')

        log.info("Parsed email address list is ${toEmails}")

        (String[])toEmails.toArray()

    }

}
