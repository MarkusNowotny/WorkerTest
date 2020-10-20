package com.example.workertest

import android.app.Application
import timber.log.Timber

class WorkerTestApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}