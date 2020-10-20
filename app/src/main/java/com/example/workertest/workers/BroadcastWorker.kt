package com.example.workertest.workers

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

class BroadcastWorker(
        private val context: Context,
        parameters: WorkerParameters
): CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result = coroutineScope {
        Result.success()
    }

}