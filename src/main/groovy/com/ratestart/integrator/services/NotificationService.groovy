package com.ratestart.integrator.services

import com.ratestart.integrator.domain.LenderType
import com.ratestart.integrator.model.PlatformProperty
import com.ratestart.integrator.repo.SubscriptionAlert
import com.ratestart.integrator.repo.SubscriptionAlertRepository
import groovy.util.logging.Slf4j
import org.apache.commons.codec.digest.DigestUtils
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

        List<String> deviceTokens = subscriptionAlertList.deviceToken

        subscriptionAlertList.forEach { it ->
            sendAndroidNotification(title, message, it.deviceToken)
        }
        //sendIosNotification(title, message, deviceTokens)

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

    static JSONObject buildIosJsonMessage(String message, List<String> deviceTokens) {
        JSONObject json = new JSONObject()

        JSONObject aps = new JSONObject()
        aps.put("alert", message)

        json.put("device_tokens", deviceTokens)
        json.put("aps", aps)
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

    void sendIosNotification(String title, String message, List<String> deviceTokens) {
        JSONObject json = buildIosJsonMessage(message, deviceTokens)
        log.info("IOS Notification JSON -> " + json.toString())

        URL url = new URL("${platformProperty.iosServerUrl}?publishkey=${platformProperty.iosPublishKey}&signature=${getSignature()}")
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setUseCaches(false)
        conn.setDoInput(true)
        conn.setDoOutput(true)

        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type","application/json")

        sendAlert(conn, json)
        log.info("IOS Alert Response: " + conn.getResponseCode() + "   :   " + conn.getResponseMessage())
        conn.disconnect()

    }

    String getSignature() {
        new String(DigestUtils.sha(platformProperty.iosSecretKey))
    }

}
