package com.szoldapps.bitcoin.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.szoldapps.bitcoin.repository.BlockchainRepository
import javax.inject.Inject

/**
 * ViewModelFactory for [MainViewModel]
 */
class MainViewModelFactory @Inject constructor(
    private val blockchainRepository: BlockchainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(blockchainRepository) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }
}
