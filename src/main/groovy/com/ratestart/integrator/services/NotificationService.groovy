package com.ratestart.integrator.services

import com.ratestart.integrator.domain.LenderType
import com.ratestart.integrator.model.PlatformProperty
import com.ratestart.integrator.repo.SubscriptionAlert
import com.ratestart.integrator.repo.SubscriptionAlertRepository
import groovy.util.logging.Slf4j
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
        String type = "RateStart"

        List<SubscriptionAlert> subscriptionAlertList = alertRepository.fetchAllUserAlerts(lenderType.id)
        if (subscriptionAlertList.isEmpty()) {
            log.info("No User Alerts found..")
            log.info("Exiting notification")
            return
        }

        URL url = new URL(platformProperty.serverUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setUseCaches(false)
        conn.setDoInput(true)
        conn.setDoOutput(true)

        conn.setRequestMethod("POST")
        conn.setRequestProperty("Authorization","key="+platformProperty.serverKey)
        conn.setRequestProperty("Content-Type","application/json")

        subscriptionAlertList.forEach { it ->

            JSONObject json = buildJsonMessage(title, message, it.deviceToken)
            log.info("Notification JSON -> " + json.toString())

            sendAlert(conn, json)
            log.info("Alert Response: " + conn.getResponseCode() + "   :   " + conn.getResponseMessage())

        }
        conn.disconnect()

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
    }

}
