package com.example.batteryindicator

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.batteryindicator.batteryindicator.BatteryIndicator
import com.example.batteryindicator.ui.theme.BatteryIndicatorTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val batteryMonitor: BatteryMonitor = BatteryMonitorImpl()



        lifecycleScope.launch {


            repeatOnLifecycle(Lifecycle.State.STARTED) {
                batteryMonitor.observeBatteryLevel(applicationContext).distinctUntilChanged().collect { batteryLevel ->
                    println("dsds battery level : $batteryLevel")
                }
            }
        }

        // Register the broadcast receiver to listen for battery changes
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(null, filter)

        setContent {


            var batterLevel by remember { mutableFloatStateOf(0.0f) }


            LaunchedEffect(Unit) {
                lifecycleScope.launch {


                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        batteryMonitor.observeBatteryLevel(applicationContext).distinctUntilChanged().collect { bl ->
                            batterLevel=bl
                        }
                    }
                }
            }

            BatteryIndicatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {


                        BatteryIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            progress = batterLevel.toInt()
                        )
                    }

                }
            }
        }
    }
}