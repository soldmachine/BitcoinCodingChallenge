package com.szoldapps.bitcoin

import android.app.Activity
import android.app.Application
import android.util.Log
import com.szoldapps.bitcoin.di.DaggerAppComponent
import com.szoldapps.bitcoin.di.RestModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

/**
 * Application class
 */
class App : Application(), HasActivityInjector {

    @Inject
    protected lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        // Timber
        val tree = if (BuildConfig.DEBUG) DebugTree() else CrashReportingTree()
        Timber.plant(tree)

        // Dagger
        DaggerAppComponent.builder()
            .app(this)
            .context(this)
            .restModule(RestModule())
            .build()
            .inject(this)
    }

    /**
     *  A tree which logs important information for crash reporting.
     *  TODO: add actual crash reporting library
     */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            // FakeCrashLibrary.log(priority, tag, message)

            if (t != null) {
                if (priority == Log.ERROR) {
                    // FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    // FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }
}
