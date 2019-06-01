package com.szoldapps.bitcoin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.szoldapps.bitcoin.R
import com.szoldapps.bitcoin.databinding.ActivityMainBinding
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
        binding.state = mainViewModel.state
        binding.lifecycleOwner = this

        mainViewModel.marketPriceData.observe(this, Observer { marketPriceData ->
            chart.apply {
                axisRight.isEnabled = false
                xAxis.apply {
                    valueFormatter = XAxisFormatter()
                    position = XAxis.XAxisPosition.BOTTOM
                }
                legend.isEnabled = false
                data = LineData(LineDataSet(marketPriceData.entries, "Label"))
            }.invalidate()
        })
        mainViewModel.loadMarketPriceData()
    }

}
