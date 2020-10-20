package com.example.workertest.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.work.WorkManager
import com.example.workertest.R
import com.example.workertest.workers.WorkerOperations
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var status: Boolean = false
    private var run: Int = 0
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action ?: return

            run++
            Snackbar.make(findViewById(android.R.id.content), "Run: $run", Snackbar.LENGTH_INDEFINITE).show()

            Timber.d("action $action")
            if (action == "WorkerTestSuccessful") {
                runBlocking {
                    delay(2000)
                    runWorkerOps(1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start).setOnClickListener {
            status = true
            runWorkerOps(1)
        }

        findViewById<Button>(R.id.stop).setOnClickListener {
            status = false
            WorkManager.getInstance(this).cancelAllWork()
        }

        registerReceiver(receiver, IntentFilter().apply {
            addAction("WorkerTestSuccessful")
        })

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun runWorkerOps(runCount: Int) {
        if (!status) return
        WorkerOperations.build(this) {
            run = runCount
        }.continuation.enqueue()
    }

}