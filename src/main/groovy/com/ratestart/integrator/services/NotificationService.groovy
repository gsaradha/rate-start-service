package com.ratestart.integrator.services

import com.ratestart.integrator.model.PlatformProperty
import groovy.util.logging.Slf4j
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class NotificationService {

    @Autowired
    PlatformProperty platformProperty

    void sendNotification() {

        String title = "RateStart Title"
        String message = "RateStart Body"
        String type = "RateStart Type"

        URL url = new URL(platformProperty.serverUrl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setUseCaches(false)
        conn.setDoInput(true)
        conn.setDoOutput(true)

        conn.setRequestMethod("POST")
        conn.setRequestProperty("Authorization","key="+platformProperty.serverKey)
        conn.setRequestProperty("Content-Type","application/json")

        JSONObject json = new JSONObject()

        JSONObject data = new JSONObject()
        data.put("title", title)
        data.put("body", message)
        data.put("type", type)

        JSONObject notification = new JSONObject()
        notification.put("title", title)
        notification.put("body", message)

        json.put("to", "e77_i4VIJbs:APA91bH2EokF2UbFDBAqHr0_HL2RYxtkxdj-ezJox0b5tuLPoI19k9IXBJBRZpxjmden0K3KuZpFjHwfCV-7zQd2cTiHJYZZVYg4AZG_zdTacEFDvP17OzNrpfEG16BPNswZQDgllNdA")
        json.put("data", data)
        json.put("notification", notification)

        log.info("Notification JSON -> " + json.toString())

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())
        wr.write(json.toString())
        wr.flush()

        log.info(conn.getResponseCode() + "   :   " + conn.getResponseMessage())
        conn.disconnect()

    }


}
