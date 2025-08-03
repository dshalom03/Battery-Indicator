package com.example.batteryindicator.di

import com.example.batteryindicator.BatteryMonitor
import com.example.batteryindicator.BatteryMonitorImpl
import com.example.batteryindicator.batteryindicator.BatteryIndicatorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::BatteryIndicatorViewModel)

    single<BatteryMonitor> { BatteryMonitorImpl() }
}