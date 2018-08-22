package com.ratestart.integrator.services

import com.notnoop.apns.APNS
import com.notnoop.apns.ApnsService
import com.notnoop.apns.internal.Utilities
import com.ratestart.integrator.domain.LenderType
import com.ratestart.integrator.model.PlatformProperty
import com.ratestart.integrator.repo.SubscriptionAlert
import com.ratestart.integrator.repo.SubscriptionAlertRepository
import groovy.util.logging.Slf4j
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.nio.charset.StandardCharsets

@Slf4j
@Service
class NotificationService {

    @Autowired
    PlatformProperty platformProperty

    @Autowired
    SubscriptionAlertRepository alertRepository

    void sendNotification(LenderType lenderType, BigDecimal rate) {

        String title = "Rate Alert!"
        String message = "${lenderType.message}${rate}"

        List<SubscriptionAlert> subscriptionAlertList = alertRepository.fetchAllUserAlerts(lenderType.id)
        if (subscriptionAlertList.isEmpty()) {
            log.info("No User Alerts found..")
            log.info("Exiting notification")
            return
        }

        subscriptionAlertList.forEach { it ->
            if (it.deviceType.equalsIgnoreCase("andriod")) {
                sendAndroidNotification(title, message, it.deviceToken)
            } else if (it.deviceType.equalsIgnoreCase("ios")) {
                sendIosNotification(message, it.deviceToken)
            }
        }

    }

    static JSONObject buildJsonMessage(String title, String message, String deviceToken) {
        JSONObject json = new JSONObject()

        JSONObject data = new JSONObject()
        data.put("title", title)
        data.put("body", message)
        data.put("type", "RateStart")

        JSONObject notification = new JSONObject()
        notification.put("title", title)
        notification.put("body", message)

        json.put("to", deviceToken)
        json.put("data", data)
        json.put("notification", notification)
        json

    }

    static void sendAlert(HttpURLConnection httpURLConnection, JSONObject jsonObject) {
        OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream())
        wr.write(jsonObject.toString())
        wr.flush()
        wr.close()
    }

    void sendAndroidNotification(String title, String message, String deviceToken) {
        JSONObject json = buildJsonMessage(title, message, deviceToken)
        log.info("Android Notification JSON -> " + json.toString())

        URL url = new URL(platformProperty.serverUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setUseCaches(false)
        conn.setDoInput(true)
        conn.setDoOutput(true)

        conn.setRequestMethod("POST")
        conn.setRequestProperty("Authorization","key="+platformProperty.serverKey)
        conn.setRequestProperty("Content-Type","application/json")

        sendAlert(conn, json)
        log.info("Android Alert Response: " + conn.getResponseCode() + "   :   " + conn.getResponseMessage())
        conn.disconnect()

    }

    void sendIosNotification(String message, String deviceToken) {
        log.info("IOS Notification APNS -> " + message)
        try {
            ApnsService service =
                    APNS.newService()
                            .withCert("/Users/saradhabalakrishnan/Desktop/PushNotification/iphone_dev.p12", platformProperty.iosCertPassword)
                            .withSandboxDestination()
                            .build()

            String payload = APNS.newPayload()
                    .alertBody(message)
                    .sound("default")
                    .build()

            service.start()
            service.push(Utilities.encodeHex(deviceToken.getBytes(StandardCharsets.UTF_8)), new String(Utilities.toUTF8Bytes(payload)))
            log.info("IOS Push Notification sent Successfully")
            service.stop()

        } catch(Exception exception) {
            log.error("IOS Push Notification failed due: ${exception.message}")
        }

    }

}
