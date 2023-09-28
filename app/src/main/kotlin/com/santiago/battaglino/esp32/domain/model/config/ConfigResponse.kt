package com.santiago.battaglino.esp32.domain.model.config


data class ConfigResponse(
    val run: Int = 0,
    val every: Int = 0,
    val status: Int = 0,
    val deepSleep: Int = 0
)
