package com.szoldapps.bitcoin.repository.mapper

import com.szoldapps.bitcoin.repository.model.MarketPriceData
import com.szoldapps.bitcoin.repository.remote.model.BlockchainDto
import org.junit.Test
import kotlin.test.assertEquals

class MarketPriceMapperKtTest {

    @Test
    fun mapToMarketPriceData() {
        // given
        val blockchainDto = getBlockchainDto()
        val expectedMarketPriceData = MarketPriceData(
            name = "name",
            description = "description"
        )

        // when
        val actualMarketPriceData = blockchainDto.mapToMarketPriceData()

        // then
        assertEquals(expectedMarketPriceData, actualMarketPriceData)

    }

    private fun getBlockchainDto(): BlockchainDto =
        BlockchainDto(
            status = "status",
            name = NAME,
            unit = "unit",
            period = "period",
            description = DESCRIPTION,
            valueDtos = emptyList()
        )

    companion object {
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
    }

}
