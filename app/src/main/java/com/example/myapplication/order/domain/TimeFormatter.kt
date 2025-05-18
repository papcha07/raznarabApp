package com.example.myapplication.order.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeFormatter {

    fun formatResponseTime(responseTime: String): String {
        val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val zonedDateTime = ZonedDateTime.parse(responseTime, inputFormatter)
        return zonedDateTime.format(outputFormatter)
    }
}