package com.sample.daggerworkmanagersample.work

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sample.daggerworkmanagersample.BuildConfig

/**
 * A factory object that creates [ListenableWorker] instances.  The factory is invoked every
 * time a work runs.
 *
 * Injects [ListenableWorker]s. The Application[Context] must extend [HasWorkerInjector]
 *
 * @see HasWorkerInjector
 */
class DaggerWorkerFactory : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        val constructor = Class.forName(workerClassName)
            .asSubclass(ListenableWorker::class.java)
            .getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)

        return constructor.newInstance(appContext, workerParameters)
            .apply { inject(this) }
    }

    companion object {
        fun inject(worker: ListenableWorker) {
            val application = worker.applicationContext
            if (application !is HasWorkerInjector) {
                throw RuntimeException("${application.javaClass} does not implement ${HasWorkerInjector::class.java.canonicalName}")
            }

            val workerInjector = (application as HasWorkerInjector).workerInjector()
            checkNotNull(workerInjector) { "${application.javaClass}.workerInjector() return null" }
            workerInjector.inject(worker)
        }
    }
}