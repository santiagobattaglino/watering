package com.santiago.battaglino.esp32.presentation.ui.server

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santiago.battaglino.esp32.data.repository.connection.ConnectionRepository
import com.santiago.battaglino.esp32.data.repository.connection.ResultRoot
import com.santiago.battaglino.esp32.data.repository.connection.ResultSendConfig
import com.santiago.battaglino.esp32.data.repository.connection.ResultServerStatus
import com.santiago.battaglino.esp32.domain.entity.Connection
import com.santiago.battaglino.esp32.domain.model.AppData
import io.ktor.server.engine.stop
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class ServerViewModel(
    private val connectionRepository: ConnectionRepository
) : ViewModel(), KoinComponent {

    private val tag = javaClass.simpleName

    private val appData: AppData by inject()

    val serverStatus = MutableLiveData<ServerStatus>()
    val connections = MutableLiveData<List<Connection>>()

    val resultRoot = MutableLiveData<ResultRoot>()
    val resultServerStatus = MutableLiveData<ResultServerStatus>()
    val resultSendConfig = MutableLiveData<ResultSendConfig>()

    enum class ServerStatus(var displayMsg: String) {
        Stopped("Server is down"), Starting("Starting server"), Started("Server is up"), Error("error")
    }

    fun startServer(server: NettyApplicationEngine) {
        try {
            serverStatus.value = ServerStatus.Starting
            server.start(wait = false)
            serverStatus.value = ServerStatus.Started
            Log.d(tag, "startServer Server Started")
        } catch (e: Exception) {
            Log.e(tag, "startServer ${e.localizedMessage}")
            serverStatus.postValue(ServerStatus.Error)
        }
    }

    fun stopServer(server: NettyApplicationEngine) {
        try {
            serverStatus.value = ServerStatus.Stopped
            Log.d(tag, "stopServer Server Stopped")
            server.stop(0, 2, TimeUnit.SECONDS)
        } catch (e: Exception) {
            Log.e(tag, "stopServer ${e.localizedMessage}")
            serverStatus.value = ServerStatus.Error
        }
    }

    fun getRoot() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                connectionRepository.getRoot()
            }
            result.data?.apply {
                resultRoot.value = ResultRoot(this)
            }
            result.error?.apply {
                resultRoot.value = ResultRoot(error = this)
            }
        }
    }

    fun getServerStatus() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                connectionRepository.getServerStatus()
            }
            result.data?.apply {
                resultServerStatus.value = ResultServerStatus(this)
            }
            result.error?.apply {
                resultServerStatus.value = ResultServerStatus(error = this)
            }
        }
    }

    fun sendConfig() {
        viewModelScope.launch {
            val result = connectionRepository.sendConfig(appData)
            result.data?.apply {
                resultSendConfig.value = ResultSendConfig(this)
            }
            result.error?.apply {
                resultSendConfig.value = ResultSendConfig(error = this)
            }
        }
    }

    fun getConnections() {
        viewModelScope.launch {
            connectionRepository.getConnections().distinctUntilChanged().collect {
                connections.value = it
            }
        }
    }

    fun deleteConnections() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                connectionRepository.nukeTable()
            }
        }
    }

    suspend fun addConnection(newConnection: Connection) {
        connectionRepository.addConnection(newConnection)
    }

    suspend fun removeConnection(userId: String) {
        connectionRepository.removeConnection(userId)
    }
}
