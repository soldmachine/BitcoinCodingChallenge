package com.szoldapps.bitcoin.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.szoldapps.bitcoin.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.chart
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
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainViewModel.state.observe(this, Observer { mainState ->
            when (mainState) {
                MainActivityState.SHOW_CHART -> chart.visibility = View.VISIBLE
                else -> chart.visibility = View.GONE
            }

        })
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
