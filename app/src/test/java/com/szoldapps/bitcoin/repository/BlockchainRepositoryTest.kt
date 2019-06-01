package com.szoldapps.bitcoin.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.willReturn
import com.szoldapps.bitcoin.repository.mapper.mapToMarketPriceData
import com.szoldapps.bitcoin.repository.remote.model.BlockchainDto
import com.szoldapps.bitcoin.repository.remote.rest.BlockchainApi
import com.szoldapps.bitcoin.util.RxSchedulerTestRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class BlockchainRepositoryTest {

    @Rule
    @JvmField
    val liveDataThreadTestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerTestRule()

    private lateinit var blockchainApi: BlockchainApi
    private lateinit var blockchainRepository: BlockchainRepository

    @Before
    fun setup() {
        blockchainApi = mock()
        blockchainRepository = BlockchainRepository(blockchainApi)
    }

    @Test
    fun getMarketPriceData() {
        // given
        val blockchainDto: BlockchainDto = getBlockchainDto()
        given { blockchainApi.marketPrice() }.willReturn { Single.just(blockchainDto) }

        // when / then
        blockchainRepository.getMarketPriceData()
            .test()
            .assertNoErrors()
            .assertValue(blockchainDto.mapToMarketPriceData())
    }

    private fun getBlockchainDto(): BlockchainDto {
        return BlockchainDto(
            status = "status",
            name = "name",
            unit = "unit",
            period = "period",
            description = "description",
            valueDtos = emptyList()

        )
    }
}
