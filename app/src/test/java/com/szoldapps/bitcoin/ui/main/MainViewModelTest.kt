package com.szoldapps.bitcoin.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import com.szoldapps.bitcoin.repository.BlockchainRepository
import com.szoldapps.bitcoin.repository.model.MarketPriceData
import com.szoldapps.bitcoin.util.RxSchedulerTestRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @Rule
    @JvmField
    val liveDataThreadTestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerTestRule()

    private lateinit var blockchainRepository: BlockchainRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        blockchainRepository = mock()
        mainViewModel = MainViewModel(blockchainRepository)
    }

    @Test
    fun loadMarketPriceData_stateProgression_success() {
        // given
        val observer = mock<Observer<MainActivityState>>()
        mainViewModel.state.observeForever(observer)

        given { blockchainRepository.getMarketPriceData() }.willReturn { Single.just(MarketPriceData()) }

        // when
        mainViewModel.loadMarketPriceData()

        // then
        inOrder(observer).apply {
            verify(observer).onChanged(MainActivityState.LOADING)
            verify(observer).onChanged(MainActivityState.SHOW_CHART)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun loadMarketPriceData_stateProgression_error() {
        // given
        val observer = mock<Observer<MainActivityState>>()
        mainViewModel.state.observeForever(observer)

        given { blockchainRepository.getMarketPriceData() }.willReturn { Single.error(Exception()) }

        // when
        mainViewModel.loadMarketPriceData()

        // then
        inOrder(observer).apply {
            verify(observer).onChanged(MainActivityState.LOADING)
            verify(observer).onChanged(MainActivityState.ERROR)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun loadMarketPriceData_marketPriceDataIsSet() {
        // given
        val observer = mock<Observer<MarketPriceData>>()
        mainViewModel.marketPriceData.observeForever(observer)

        val marketPriceData = MarketPriceData("name", "description")
        given { blockchainRepository.getMarketPriceData() }.willReturn { Single.just(marketPriceData) }

        // when
        mainViewModel.loadMarketPriceData()

        // then
        verify(observer).onChanged(marketPriceData)
        verifyNoMoreInteractions(observer)
    }
}
