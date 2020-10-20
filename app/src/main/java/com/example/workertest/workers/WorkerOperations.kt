package com.example.workertest.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.*

class WorkerOperations private constructor(val continuation: WorkContinuation) {

    companion object {
        fun build(context: Context, block: Builder.() -> Unit = {}) = Builder(context).apply(block).build()
    }

    class Builder(private val context: Context) {

        private val worker: OneTimeWorkRequest
            get() = OneTimeWorkRequestBuilder<IdleWorker>().build()

        @SuppressLint("EnqueueWork")
        fun build(): WorkerOperations {
            var continuation = WorkManager.getInstance(context).beginUniqueWork(
                    "Worker_Test",
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequestBuilder<IdleWorker>().build()
            )

            // 15 synchronous 'requests'
            repeat(15) {
                continuation = continuation.then(worker)
            }

            // 25 times parallel 'requests'
            continuation = continuation.then((0 until 25).map { worker })

            // 50 times parallel 'requests'
            continuation = continuation.then((0 until 50).map { worker })

            continuation = continuation.then(OneTimeWorkRequestBuilder<BroadcastWorker>().build())

            return WorkerOperations(continuation)
        }

    }

}