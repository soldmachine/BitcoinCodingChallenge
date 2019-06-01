package com.szoldapps.bitcoin.ui.main.chart

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.szoldapps.bitcoin.R
import com.szoldapps.bitcoin.util.EXTENDED_DATE_FORMAT
import com.szoldapps.bitcoin.util.toFormattedDate
import kotlinx.android.synthetic.main.view_marker.view.markerDateTv
import kotlinx.android.synthetic.main.view_marker.view.markerValueTv

/**
 * Custom marker view that is shown if a value is selected in the chart.
 */
class CustomMarkerView(
    context: Context,
    layoutResource: Int
) : MarkerView(context, layoutResource) {

    private var mOffset: MPPointF? = null

    /**
     * calls back everytime the MarkerView is redrawn, can be used to update the content (user-interface)
     */
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        entry?.let {
            markerValueTv.text = context.getString(R.string.marker_template, entry.y.toString())
            markerDateTv.text = entry.x.toFormattedDate(EXTENDED_DATE_FORMAT)
        }

        // this will perform necessary layouting
        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF? {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
        return mOffset
    }
}
