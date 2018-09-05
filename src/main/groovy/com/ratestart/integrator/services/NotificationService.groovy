package com.ratestart.integrator.services

import org.apache.log4j.BasicConfigurator

import javapns.*
import javapns.notification.PushNotificationPayload
import javapns.notification.PushedNotification
import javapns.notification.ResponsePacket
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
                //Sample Device Token - a7bd83863bf27b9a00d9d35ab82bd65f4cb01df2a112694a56750d90e4f7ab79
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
        log.info("IOS Notification token -> " + deviceToken)

        BasicConfigurator.configure()

        try {

            InputStream inStream = new FileInputStream("/Users/saradhabalakrishnan/Desktop/PushNotification/Certificates_RS.p12")

            PushNotificationPayload payload = PushNotificationPayload.complex()
            payload.addAlert(message)
            payload.addBadge(1)
            payload.addSound("default")
            payload.addCustomDictionary("id", "1")
            log.info(payload.toString())

            List <PushedNotification> pushedNotifications = Push.payload(payload, inStream, "ratestart", false, [deviceToken]);

            for (PushedNotification pushedNotification: pushedNotifications) {

                if (pushedNotification.isSuccessful()) {
                    /* APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT */
                    log.info("PUSH NOTIFICATION SENT SUCCESSFULLY TO: ${pushedNotification.getDevice().getToken()}")
                    /* STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY */

                } else {
                    String invalidToken = pushedNotification.getDevice().getToken()
                    /* ADD CODE HERE TO REMOVE invalidToken FROM YOUR DATABASE */
                    /* FIND OUT MORE ABOUT WHAT THE PROBLEM WAS */

                    Exception exception = pushedNotification.getException()
                    exception.printStackTrace()
                    /* IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY APPLE, GET IT */

                    ResponsePacket responsePacket = pushedNotification.getResponse()
                    if (responsePacket != null) {
                        log.info(responsePacket.getMessage())
                    }
                }
            }

            inStream.close()

        } catch (Exception iosException) {
            log.error("********** IOS Push Notification Exception -> ${iosException.getMessage()}")
        }
    }

}


