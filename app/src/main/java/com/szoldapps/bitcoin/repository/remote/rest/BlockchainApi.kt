package com.szoldapps.bitcoin.repository.remote.rest

import com.szoldapps.bitcoin.repository.remote.model.BlockchainDto
import io.reactivex.Single
import retrofit2.http.GET

interface BlockchainApi {

    /**
     * Returns Market Prices (in USD)
     */
    @GET("/charts/market-price")
    fun marketPrice(): Single<BlockchainDto>

}
