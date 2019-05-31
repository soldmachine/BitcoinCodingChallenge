package com.szoldapps.bitcoin.repository.mapper

import com.github.mikephil.charting.data.Entry
import com.szoldapps.bitcoin.repository.model.MarketPriceData
import com.szoldapps.bitcoin.repository.remote.model.BlockchainDto

/**
 * Maps a [BlockchainDto] to a [MarketPriceData] object.
 */
fun BlockchainDto.mapToMarketPriceData(): MarketPriceData =
    MarketPriceData(
        name = name ?: "",
        description = description ?: "",
        entries = valueDtos?.map { value -> Entry(value.x.toFloat(), value.y.toFloat()) } ?: emptyList()
    )
