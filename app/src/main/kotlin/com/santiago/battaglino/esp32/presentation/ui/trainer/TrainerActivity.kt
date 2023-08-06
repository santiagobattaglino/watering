package com.santiago.battaglino.esp32.presentation.ui.trainer

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.santiago.battaglino.esp32.R
import com.santiago.battaglino.esp32.data.repository.SharedPreferenceUtils
import com.santiago.battaglino.esp32.databinding.ActivityTrainerBinding
import com.santiago.battaglino.esp32.presentation.ui.base.BaseServerActivity
import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel
import com.santiago.battaglino.esp32.util.Arguments
import com.santiago.battaglino.esp32.util.Constants
import com.santiago.battaglino.esp32.util.NetworkUtil
import org.koin.android.ext.android.inject

/**
 * Bottom Nav Activity running Ktor server with MQTT Broker, client and REST API.
 * Starting server onStart and stopping onStop.
 * Showing Header with IP, user and team selected.
 * NewTeamSessionFragment as startDestination.
 */
class TrainerActivity : BaseServerActivity() {

    private val tag = javaClass.simpleName
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityTrainerBinding.inflate(layoutInflater)
    }
    private val sp: SharedPreferenceUtils by inject()
    private val startDestination = R.id.config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getServerStatus()

        /*lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    viewModel.getServerStatus()
                    delay(Constants.SERVER_STATUS_PING_INTERVAL)
                }
            }
        }*/

        viewBinding.apply {
            setContentView(root)
            // header.trainerName.text = appData.getUsername()
            header.ipAddressText.text = getString(
                R.string.localIpAddressMessage,
                NetworkUtil.getIpAddressInLocalNetwork(this@TrainerActivity, sp, appData),
                Constants.SERVER_PORT.toString()
            )
            // header.teamName.text = appData.team?.name
        }
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        intent.extras?.let {
            setNavGraph(it.getInt(Arguments.START_DESTINATION), navController)
        } ?: run {
            setNavGraph(startDestination, navController)
        }

        viewBinding.bottomNavView.setupWithNavController(navController)
    }

    private fun setNavGraph(startDestinationId: Int, navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.trainer_nav_graph)
        graph.setStartDestination(startDestinationId)
        navController.setGraph(graph, null)
    }

    override fun observe() {
        viewModel.resultServerStatus.observe(this) {
            it.error?.let {
                uiServerStopped()
                Toast.makeText(this, getString(R.string.communication_lost), Toast.LENGTH_LONG)
                    .show()
                //finish()
            }
            it.data?.let { serverStatus ->
                when (serverStatus.server) {
                    ServerViewModel.ServerStatus.Stopped -> uiServerStopped()
                    ServerViewModel.ServerStatus.Starting -> uiServerStopped()
                    ServerViewModel.ServerStatus.Started -> uiServerStarted()
                    ServerViewModel.ServerStatus.Error -> uiServerStopped()
                }
            }
        }
    }

    private fun uiServerStopped() {
        appData.serverStatus = ServerViewModel.ServerStatus.Stopped
        viewBinding.header.serverStatus.text = getString(R.string.server_down)
        viewBinding.header.serverStatus.setTextColor(getColor(R.color.error))
        Toast.makeText(this, getString(R.string.server_communication_lost), Toast.LENGTH_LONG)
            .show()
        //finish()
    }

    private fun uiServerStarted() {
        appData.serverStatus = ServerViewModel.ServerStatus.Started
        viewBinding.header.serverStatus.text = getString(R.string.server_up)
        viewBinding.header.serverStatus.setTextColor(getColor(R.color.ok))
    }
}