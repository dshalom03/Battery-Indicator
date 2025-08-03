package com.example.batteryindicator.batteryindicator

import androidx.lifecycle.ViewModel
import com.example.batteryindicator.BatteryMonitor

class BatteryIndicatorViewModel(val batteryMonitor: BatteryMonitor): ViewModel() {
    fun doit() {
        println("dsds di")
    }


}