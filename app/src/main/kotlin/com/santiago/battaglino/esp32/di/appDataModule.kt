package com.santiago.battaglino.esp32.di

import com.santiago.battaglino.esp32.domain.model.AppData
import org.koin.dsl.module

val appDataModule = module {
    single {
        AppData()
    }
}
