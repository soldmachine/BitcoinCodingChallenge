package com.szoldapps.bitcoin.ui.main.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.szoldapps.bitcoin.util.toFormattedDate

/**
 * X-Axis formatter. Transforms unix timestamp to formatted date string.
 */
class XAxisFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String = value.toFormattedDate()

}
