package com.santiago.battaglino.esp32.domain.model.config


data class ConfigResponse(
    val run: Int,
    val every: Int,
    val status: Int,
    val deepSleep: Int
)
