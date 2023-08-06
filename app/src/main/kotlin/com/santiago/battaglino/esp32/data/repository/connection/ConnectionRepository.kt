package com.santiago.battaglino.esp32.data.repository.connection

import com.santiago.battaglino.esp32.domain.entity.Connection
import com.santiago.battaglino.esp32.domain.model.AppData
import kotlinx.coroutines.flow.Flow

interface ConnectionRepository {
    fun getConnections(): Flow<List<Connection>>
    suspend fun nukeTable()
    suspend fun addConnection(newConnection: Connection): Long
    suspend fun getConnection(userId: String): Connection?
    suspend fun removeConnection(userId: String): Int
    suspend fun getRoot(): ResultRoot
    suspend fun getServerStatus(): ResultServerStatus
    suspend fun sendConfig(appData: AppData): ResultSendConfig
}