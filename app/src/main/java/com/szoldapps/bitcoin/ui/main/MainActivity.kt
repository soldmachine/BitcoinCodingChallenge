package com.szoldapps.bitcoin.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.szoldapps.bitcoin.R
import com.szoldapps.bitcoin.repository.model.MarketPriceData
import com.szoldapps.bitcoin.ui.main.chart.CustomMarkerView
import com.szoldapps.bitcoin.ui.main.chart.XAxisFormatter
import com.szoldapps.bitcoin.ui.main.chart.YAxisFormatter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.mainDescriptionTv
import kotlinx.android.synthetic.main.activity_main.mainNameTv
import kotlinx.android.synthetic.main.activity_main.showChartGroup
import kotlinx.android.synthetic.main.view_error.errorBt
import kotlinx.android.synthetic.main.view_error.errorCl
import kotlinx.android.synthetic.main.view_loading.loadingCl
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.mainChart as chart
import kotlinx.android.synthetic.main.activity_main.mainRefreshIv as refreshIv

/**
 * Main activity that displays all content.
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.state.observe(this, Observer { state ->
            updateViewsAccordingToState(state)
        })
        mainViewModel.marketPriceData.observe(this, Observer { marketPriceData ->
            setNameAndDescription(marketPriceData)
            formatAndUpdateChart(marketPriceData)
        })
        mainViewModel.loadMarketPriceData()

        // Refresh handling
        refreshIv.setOnClickListener {
            mainViewModel.loadMarketPriceData()
        }

        // Error handling
        errorBt.setOnClickListener {
            mainViewModel.loadMarketPriceData()
        }
    }

    private fun updateViewsAccordingToState(state: MainActivityState?) {
        when (state ?: return) {
            MainActivityState.LOADING -> {
                loadingCl.visibility = View.VISIBLE
                showChartGroup.visibility = View.GONE
                errorCl.visibility = View.GONE
            }
            MainActivityState.SHOW_CHART -> {
                loadingCl.visibility = View.GONE
                showChartGroup.visibility = View.VISIBLE
                errorCl.visibility = View.GONE
            }
            MainActivityState.ERROR -> {
                loadingCl.visibility = View.GONE
                showChartGroup.visibility = View.GONE
                errorCl.visibility = View.VISIBLE
            }
        }
    }

    private fun setNameAndDescription(marketPriceData: MarketPriceData) {
        mainNameTv.text = marketPriceData.name
        mainDescriptionTv.text = marketPriceData.description
    }

    @SuppressWarnings("MagicNumber")
    private fun formatAndUpdateChart(marketPriceData: MarketPriceData) {
        chart.apply {
            description = null
            legend.isEnabled = false
            axisRight.isEnabled = false
            val lightGray = ContextCompat.getColor(context, R.color.lightGray)
            axisLeft.apply {
                gridColor = lightGray
                valueFormatter = YAxisFormatter()
            }
            xAxis.apply {
                gridColor = lightGray
                valueFormatter = XAxisFormatter(viewPortHandler)
                position = XAxis.XAxisPosition.BOTTOM
            }
            val lineDataSet = LineDataSet(marketPriceData.entries, null).apply {
                color = ContextCompat.getColor(context, R.color.colorPrimary)
                lineWidth = 3f
                setDrawCircles(false)
                setDrawValues(false)
                // smooth out line
                mode = LineDataSet.Mode.CUBIC_BEZIER
            }
            marker = CustomMarkerView(context, R.layout.view_marker)
            data = LineData(lineDataSet)
        }.invalidate()
    }
}
