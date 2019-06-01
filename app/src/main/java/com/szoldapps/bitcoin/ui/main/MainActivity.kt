package com.szoldapps.bitcoin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.szoldapps.bitcoin.R
import com.szoldapps.bitcoin.databinding.ActivityMainBinding
import com.szoldapps.bitcoin.ui.main.chart.CustomMarkerView
import com.szoldapps.bitcoin.ui.main.chart.XAxisFormatter
import com.szoldapps.bitcoin.ui.main.chart.YAxisFormatter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.chart
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainVm = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.marketPriceData.observe(this, Observer { marketPriceData ->
            chart.apply {
                description = null
                legend.isEnabled = false
                axisRight.isEnabled = false
                axisLeft.valueFormatter = YAxisFormatter()
                xAxis.apply {
                    setDrawGridLines(false)
                    valueFormatter = XAxisFormatter()
                    position = XAxis.XAxisPosition.BOTTOM
                }
                val lineDataSet = LineDataSet(marketPriceData.entries, "Label").apply {
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
        })
        mainViewModel.loadMarketPriceData()
    }

}
