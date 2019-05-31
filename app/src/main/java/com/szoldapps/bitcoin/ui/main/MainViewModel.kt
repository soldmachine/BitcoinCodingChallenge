package com.szoldapps.bitcoin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szoldapps.bitcoin.repository.BlockchainRepository
import com.szoldapps.bitcoin.repository.model.MarketPriceData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(
    private val blockchainRepository: BlockchainRepository
) : ViewModel() {

    val state = MutableLiveData<MainActivityState>()
    val marketPriceData = MutableLiveData<MarketPriceData>()
    private val disposables = CompositeDisposable()

    /**
     * Loads market price data and updates [state] accordingly
     */
    fun loadMarketPriceData() {
        state.postValue(MainActivityState.LOADING)
        blockchainRepository.getMarketPriceData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { marketPriceDataTemp ->
                    marketPriceData.postValue(marketPriceDataTemp)
                    state.postValue(MainActivityState.SHOW_CHART)
                },
                onError = { throwable ->
                    Timber.e(throwable, "Failed to retrieve the market price data")
                    state.postValue(MainActivityState.ERROR)
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
