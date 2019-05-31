package com.szoldapps.bitcoin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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

    val state = MutableLiveData<MainActivityState>()
    val lineDataSet = MutableLiveData<LineDataSet>()
    private val disposables = CompositeDisposable()

    fun loadData() {
        state.postValue(MainActivityState.LOADING)
        blockchainRepository.getMarketPriceData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { blockchainData ->
                    val entries = blockchainData.values?.map { value -> Entry(value.x.toFloat(), value.y.toFloat()) }
                    lineDataSet.postValue(LineDataSet(entries, "Label"))
                    state.postValue(MainActivityState.SHOW_CHART)
                    Timber.i("got something\n: $blockchainData")
                },
                onError = { throwable ->
                    Timber.e(throwable, "Failed to get market price data")
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
