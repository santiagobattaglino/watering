package com.santiago.battaglino.esp32.di

import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ServerViewModel(get()) }
}
