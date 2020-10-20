package com.example.workertest.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class IdleWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result = coroutineScope {

        val jobs = (0 until 50).map {
            async {
                delay(50)
                // Example 'api' response
                true
            }
        }

        val requests = jobs.awaitAll()

        // Check if all 'request' are successful
        if (requests.all { it }) {
            Result.success()
        } else {
            Result.failure()
        }

    }

}