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
                axisRight.isEnabled = false
                xAxis.apply {
                    setDrawGridLines(false)
                    valueFormatter = XAxisFormatter()
                    position = XAxis.XAxisPosition.BOTTOM
                }
                legend.isEnabled = false
                val lineDataSet = LineDataSet(marketPriceData.entries, "Label")
                val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                lineDataSet.color = colorPrimary
                lineDataSet.lineWidth = 3f
                lineDataSet.setDrawCircles(false)
                lineDataSet.setDrawValues(false)

                setTouchEnabled(true)
                val marker = CustomMarkerView(context, R.layout.view_marker)
                setMarker(marker)
                //lineDataSet.setCircleColors(colorPrimary)
                data = LineData(lineDataSet)
            }.invalidate()
        })
        mainViewModel.loadMarketPriceData()
    }

}
