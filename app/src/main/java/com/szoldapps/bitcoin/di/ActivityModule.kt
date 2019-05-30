package com.szoldapps.bitcoin.di

import com.szoldapps.bitcoin.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides AndroidInjector for activities
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}

