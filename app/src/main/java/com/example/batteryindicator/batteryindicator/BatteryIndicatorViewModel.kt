package com.example.batteryindicator.batteryindicator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batteryindicator.BatteryMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BatteryIndicatorViewModel(val batteryMonitor: BatteryMonitor) : ViewModel() {

    lateinit var state: StateFlow<Float>

    init {
        viewModelScope.launch {
            state = batteryMonitor.observeBatteryLevel().distinctUntilChanged().stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(), initialValue = 0f
            )
        }
    }
}