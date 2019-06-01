package com.szoldapps.bitcoin.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val EXTENDED_DATE_FORMAT = "EEE, dd.MM.YYYY hh:mm"
const val MONTH_YEAR_DATE_FORMAT = "MMM.YY"
const val DAY_MONTH_YEAR_DATE_FORMAT = "dd.MM"

/**
 * Converts unix timestamp (in s) to formatted date (default = [MONTH_YEAR_DATE_FORMAT])
 */
fun Float.toFormattedDate(dateFormat: String): String {
    return try {
        SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(this.toLong() * 1000)) ?: this.toString()
    } catch (e: Exception) {
        when (e) {
            is NullPointerException,
            is IllegalArgumentException -> this.toString()
            else -> throw e
        }
    }
}
