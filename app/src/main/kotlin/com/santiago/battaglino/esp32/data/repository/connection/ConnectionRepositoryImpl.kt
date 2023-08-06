package com.santiago.battaglino.esp32.data.repository.connection

import com.haroldadmin.cnradapter.NetworkResponse
import com.santiago.battaglino.esp32.data.network.api.Api
import com.santiago.battaglino.esp32.data.network.error.ErrorObject
import com.santiago.battaglino.esp32.data.room.dao.ConnectionDao
import com.santiago.battaglino.esp32.domain.entity.Connection
import com.santiago.battaglino.esp32.domain.model.AppData
import com.santiago.battaglino.esp32.domain.model.base.Data
import com.santiago.battaglino.esp32.domain.model.config.ConfigResponse
import com.santiago.battaglino.esp32.domain.model.status.ServerStatusResponse

class ConnectionRepositoryImpl(
    private val api: Api, private val connectionDao: ConnectionDao
) : ConnectionRepository {
    override fun getConnections() = connectionDao.getConnections()
    override suspend fun nukeTable() = connectionDao.nukeTable()
    override suspend fun addConnection(newConnection: Connection) =
        connectionDao.insert(newConnection)

    override suspend fun getConnection(userId: String): Connection? {
        return connectionDao.getConnection(userId)
    }

    override suspend fun removeConnection(userId: String) = connectionDao.removeConnection(userId)

    override suspend fun getRoot(): ResultRoot {
        return when (val result = api.getRoot()) {
            is NetworkResponse.Success -> {
                ResultRoot(result.body, null)
            }

            is NetworkResponse.ServerError -> {
                ResultRoot(
                    error = ErrorObject(
                        result.code ?: ErrorObject.UNKNOWN, result.body?.error
                    )
                )
            }

            is NetworkResponse.NetworkError -> {
                ResultRoot(
                    error = ErrorObject(
                        ErrorObject.NO_CONNECTION, result.error.message
                    )
                )
            }

            is NetworkResponse.UnknownError -> {
                ResultRoot(
                    error = ErrorObject(
                        ErrorObject.UNKNOWN, (result.response?.raw() ?: result.error).toString()
                    )
                )
            }
        }
    }

    override suspend fun getServerStatus(): ResultServerStatus {
        return when (val result = api.getServerStatus()) {
            is NetworkResponse.Success -> {
                ResultServerStatus(result.body, null)
            }

            is NetworkResponse.ServerError -> {
                ResultServerStatus(
                    error = ErrorObject(
                        result.code ?: ErrorObject.UNKNOWN, result.body?.error
                    )
                )
            }

            is NetworkResponse.NetworkError -> {
                ResultServerStatus(
                    error = ErrorObject(
                        ErrorObject.NO_CONNECTION, result.error.message
                    )
                )
            }

            is NetworkResponse.UnknownError -> {
                ResultServerStatus(
                    error = ErrorObject(
                        ErrorObject.UNKNOWN, (result.response?.raw() ?: result.error).toString()
                    )
                )
            }
        }
    }

    override suspend fun sendConfig(appData: AppData): ResultSendConfig {
        return when (val result =
            api.sendConfig(appData.deepSleep, appData.every, appData.run, appData.isRunning)) {
            is NetworkResponse.Success -> {
                ResultSendConfig(result.body, null)
            }

            is NetworkResponse.ServerError -> {
                ResultSendConfig(
                    error = ErrorObject(
                        result.code ?: ErrorObject.UNKNOWN, result.body?.error
                    )
                )
            }

            is NetworkResponse.NetworkError -> {
                ResultSendConfig(
                    error = ErrorObject(
                        ErrorObject.NO_CONNECTION, result.error.message
                    )
                )
            }

            is NetworkResponse.UnknownError -> {
                ResultSendConfig(
                    error = ErrorObject(
                        ErrorObject.UNKNOWN, (result.response?.raw() ?: result.error).toString()
                    )
                )
            }
        }
    }
}

data class ResultRoot(
    val data: Data? = null, val error: ErrorObject? = null
)

data class ResultServerStatus(
    val data: ServerStatusResponse? = null, val error: ErrorObject? = null
)

data class ResultSendConfig(
    val data: ConfigResponse? = null, val error: ErrorObject? = null
)