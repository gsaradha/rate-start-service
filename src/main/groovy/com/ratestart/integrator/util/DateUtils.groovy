package com.ratestart.integrator.util

import java.text.DateFormat
import java.text.SimpleDateFormat

class DateUtils {

    static String getDate(Date date) {
        if (date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
            dateFormat.format(date)
        }
    }

    static Date getDate(String date) {
        if (date) {
            new SimpleDateFormat("yyyy-MM-dd").parse(date)
        }
    }

}
