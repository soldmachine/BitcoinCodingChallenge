package com.szoldapps.bitcoin.repository.model

import com.github.mikephil.charting.data.Entry

/**
 * App internal model that includes all market price relevant data.
 */
data class MarketPriceData(
    val name: String,
    val description: String,
    val entries: List<Entry>
)
