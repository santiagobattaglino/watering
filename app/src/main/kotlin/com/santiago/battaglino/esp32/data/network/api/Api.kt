package com.santiago.battaglino.esp32.data.network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.santiago.battaglino.esp32.data.network.error.ErrorResponse
import com.santiago.battaglino.esp32.domain.entity.*
import com.santiago.battaglino.esp32.domain.model.base.Data
import com.santiago.battaglino.esp32.domain.model.config.ConfigResponse
import com.santiago.battaglino.esp32.domain.model.status.ServerStatusResponse
import retrofit2.http.*

interface Api {

    @GET("/")
    suspend fun getRoot(): NetworkResponse<Data, ErrorResponse>

    // STATUS

    @GET("/status")
    suspend fun getServerStatus(): NetworkResponse<ServerStatusResponse, ErrorResponse>

    // CONFIG

    @GET("/config")
    suspend fun sendConfig(
        @Query("deepSleep") deepSleep: Int,
        @Query("every") every: Int,
        @Query("run") run: Int,
        @Query("status") status: Int
    ): NetworkResponse<ConfigResponse, ErrorResponse>
}