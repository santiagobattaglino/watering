package com.santiago.battaglino.esp32.domain.model

import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel

data class AppData(
    var serverIpAddress: String? = null,
    var serverStatus: ServerViewModel.ServerStatus = ServerViewModel.ServerStatus.Stopped,
    var every: Int = 3600,
    var run: Int = 180,
    var isRunning: Int = 0,
    var deepSleep: Int = 0,
)