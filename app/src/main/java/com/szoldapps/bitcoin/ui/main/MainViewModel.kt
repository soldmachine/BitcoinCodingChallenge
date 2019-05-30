package com.szoldapps.bitcoin.ui.main

import androidx.lifecycle.ViewModel
import com.szoldapps.bitcoin.repository.BlockchainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(
    private val blockchainRepository: BlockchainRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun loadData() {
        blockchainRepository.getMarketPriceData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { blockchainData ->
                    Timber.i("got something\n: $blockchainData")
                },
                onError = { throwable ->
                    Timber.e(throwable, "Failed to get market price data")
                    // loginState.value = SuccessState()
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
