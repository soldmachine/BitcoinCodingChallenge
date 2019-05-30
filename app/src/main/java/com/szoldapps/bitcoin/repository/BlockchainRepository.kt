package com.szoldapps.bitcoin.repository

import com.szoldapps.bitcoin.repository.remote.model.BlockchainData
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
    fun getMarketPriceData(): Single<BlockchainData> = blockchainApi.marketPrice()

}
