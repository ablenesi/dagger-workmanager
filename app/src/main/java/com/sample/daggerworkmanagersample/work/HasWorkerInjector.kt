package com.sample.daggerworkmanagersample.work

import androidx.work.ListenableWorker
import dagger.android.AndroidInjector

/** Provides an [AndroidInjector] of [ListenableWorker]s. */
interface HasWorkerInjector {

    /** Returns an [AndroidInjector] of [ListenableWorker]s. */
    fun workerInjector(): AndroidInjector<ListenableWorker>
}