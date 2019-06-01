package com.szoldapps.bitcoin.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Rule that overrides all all schedulers with [Schedulers.trampoline]
 */
class RxSchedulerTestRule : TestRule {

    private val trampolineScheduler = Schedulers.trampoline()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                val schedulerFunc: (Scheduler) -> Scheduler = { trampolineScheduler }

                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { trampolineScheduler }

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(schedulerFunc)
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunc)
                RxJavaPlugins.setComputationSchedulerHandler(schedulerFunc)

                base?.evaluate()

                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}
