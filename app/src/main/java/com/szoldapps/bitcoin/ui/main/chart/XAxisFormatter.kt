package com.szoldapps.bitcoin.ui.main.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.szoldapps.bitcoin.util.DAY_MONTH_YEAR_DATE_FORMAT
import com.szoldapps.bitcoin.util.MONTH_YEAR_DATE_FORMAT
import com.szoldapps.bitcoin.util.toFormattedDate

/**
 * X-Axis formatter. Transforms unix timestamp to formatted date string.
 */
class XAxisFormatter(private val viewPortHandler: ViewPortHandler) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val dateFormat = if (viewPortHandler.scaleX > 3) DAY_MONTH_YEAR_DATE_FORMAT else MONTH_YEAR_DATE_FORMAT
        return value.toFormattedDate(dateFormat)
    }

}
