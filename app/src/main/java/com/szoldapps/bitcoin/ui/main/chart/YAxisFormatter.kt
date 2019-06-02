package com.szoldapps.bitcoin.ui.main.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Y-Axis formatter. Prepends $ sign to value.
 */
class YAxisFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String = "$$value"
}
