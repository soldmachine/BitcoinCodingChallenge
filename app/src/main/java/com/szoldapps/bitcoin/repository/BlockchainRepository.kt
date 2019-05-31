package com.szoldapps.bitcoin.repository

import com.szoldapps.bitcoin.repository.mapper.mapToMarketPriceData
import com.szoldapps.bitcoin.repository.model.MarketPriceData
import com.szoldapps.bitcoin.repository.remote.rest.BlockchainApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Repository that provides blockchain related data
 */
class BlockchainRepository @Inject constructor(
    private val blockchainApi: BlockchainApi
) {

    /**
     * Retrieves market price data via [BlockchainApi.marketPrice]
     */
    fun getMarketPriceData(): Single<MarketPriceData> =
        blockchainApi.marketPrice().map { blockchainDto -> blockchainDto.mapToMarketPriceData() }

}
