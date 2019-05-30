package com.szoldapps.bitcoin.di

import android.app.Application
import android.content.Context
import com.szoldapps.bitcoin.App
import com.szoldapps.bitcoin.repository.BlockchainRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Component to provide injected classes
 */
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RestModule::class,
        ActivityModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(application: App): Builder

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun appModule(appModule: AppModule): Builder

        @BindsInstance
        fun restModule(restModule: RestModule): Builder

        fun build(): AppComponent
    }

}
