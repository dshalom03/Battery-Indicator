package com.example.batteryindicator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface BatteryMonitor {
    suspend fun observeBatteryLevel(): Flow<Float>
}

class BatteryMonitorImpl(val context: Context) : BatteryMonitor {
    override suspend fun observeBatteryLevel(): Flow<Float> =
        callbackFlow {
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val batteryLevel: Float? = intent.let { intent ->
                        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                        level * 100 / scale.toFloat()
                    }
                    trySend(batteryLevel ?: 0f)

                }
            }
            ContextCompat.registerReceiver(
                context,
                receiver,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            awaitClose { context.unregisterReceiver(receiver) }
        }

}


