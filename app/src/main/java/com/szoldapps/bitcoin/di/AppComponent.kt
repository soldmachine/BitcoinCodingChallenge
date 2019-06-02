package com.szoldapps.bitcoin.di

import android.content.Context
import com.szoldapps.bitcoin.App
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
        RestModule::class,
        ActivityModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    /**
     * Builder interface to set available modules
     */
    @Component.Builder
    interface Builder {

        /**
         * Binds [App] instance
         */
        @BindsInstance
        fun app(application: App): Builder

        /**
         * Binds [Context] instance
         */
        @BindsInstance
        fun context(context: Context): Builder

        /**
         * Binds [RestModule] instance
         */
        @BindsInstance
        fun restModule(restModule: RestModule): Builder

        /**
         * Builds [AppComponent]
         */
        fun build(): AppComponent
    }
}
