package com.santiago.battaglino.esp32.presentation.ui.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.santiago.battaglino.esp32.data.repository.SharedPreferenceUtils
import com.santiago.battaglino.esp32.domain.model.base.Data
import com.santiago.battaglino.esp32.domain.model.config.ConfigResponse
import com.santiago.battaglino.esp32.domain.model.status.ServerStatusResponse
import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel
import com.santiago.battaglino.esp32.util.Arguments
import com.santiago.battaglino.esp32.util.Constants
import com.santiago.battaglino.esp32.util.NotificationUtil
import io.ktor.serialization.gson.gson
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.slf4j.event.Level
import java.text.DateFormat

abstract class BaseServerActivity : BaseActivity() {

    private val tag = javaClass.simpleName
    protected val viewModel: ServerViewModel by viewModel()
    private val sp: SharedPreferenceUtils by inject()

    private val server by lazy {
        embeddedServer(Netty, Constants.SERVER_PORT, watchPaths = emptyList()) {
            install(DefaultHeaders)
            install(Compression)
            install(CallLogging) {
                level = Level.INFO
            }
            install(ContentNegotiation) {
                gson {
                    setDateFormat(DateFormat.LONG)
                    setPrettyPrinting()
                }
            }

            routing {
                rootRoute()
                statusRoute()
                configRoute()
            }
        }
    }

    private fun Route.rootRoute() {
        get("/") {
            call.respond(Data("All good here in ${Build.MODEL}"))
        }
    }

    private fun Route.statusRoute() {
        get("/status") {
            call.respond(
                ServerStatusResponse(
                    viewModel.serverStatus.value ?: ServerViewModel.ServerStatus.Stopped
                )
            )
        }
    }

    private fun Route.configRoute() {
        get("/config") {
            call.request.queryParameters["batteryLevel"]?.let { batteryLevel ->
                sp.saveString(Arguments.BATTERY_LEVEL, batteryLevel)
            }
            val response =
                ConfigResponse(appData.run, appData.every, appData.isRunning, appData.deepSleep)
            val responseString: String = Gson().toJson(response)
            Log.d(tag, responseString)
            call.respond(responseString)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationUtil.createNotificationChannel(this)
        viewModel.startServer(server)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteConnections()
        // viewModel.stopServer(server) // TODO trigger manually
    }
}