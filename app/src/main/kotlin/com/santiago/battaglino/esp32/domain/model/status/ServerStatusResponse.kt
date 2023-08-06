package com.santiago.battaglino.esp32.domain.model.status

import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel

data class ServerStatusResponse(
    val server: ServerViewModel.ServerStatus = ServerViewModel.ServerStatus.Stopped,
)
