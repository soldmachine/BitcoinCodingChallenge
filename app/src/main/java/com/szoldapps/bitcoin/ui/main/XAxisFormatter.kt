package com.szoldapps.bitcoin.ui.main

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * X-Axis formatter. Transforms unix timestamp to formatted date string.
 */
class XAxisFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val date = SimpleDateFormat(AXIS_DATE_FORMAT, Locale.getDefault()).format(Date(value.toLong() * 1000))
        return date ?: value.toString()
    }

    companion object {
        private const val AXIS_DATE_FORMAT = "MMM-YY"
    }

}
