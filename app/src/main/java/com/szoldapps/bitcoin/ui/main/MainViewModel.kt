package com.szoldapps.bitcoin.ui.main

import androidx.lifecycle.LiveData
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

/**
 * Main view model, used by [MainActivity]
 */
class MainViewModel(
    private val blockchainRepository: BlockchainRepository
) : ViewModel() {

    private val _state = MutableLiveData<MainActivityState>()
    val state: LiveData<MainActivityState> = _state

    private val _marketPriceData = MutableLiveData<MarketPriceData>()
    val marketPriceData: LiveData<MarketPriceData> = _marketPriceData

    private val disposables = CompositeDisposable()

    /**
     * Loads market price data and updates [state] accordingly
     */
    fun loadMarketPriceData() {
        _state.postValue(MainActivityState.LOADING)
        blockchainRepository.getMarketPriceData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { marketPriceDataTemp ->
                    _marketPriceData.postValue(marketPriceDataTemp)
                    _state.postValue(MainActivityState.SHOW_CHART)
                },
                onError = { throwable ->
                    Timber.e(throwable, "Failed to retrieve the market price data")
                    _state.postValue(MainActivityState.ERROR)
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
