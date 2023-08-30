package com.santiago.battaglino.esp32.presentation.ui.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.santiago.battaglino.esp32.databinding.FragmentConfigBinding
import com.santiago.battaglino.esp32.presentation.ui.base.BaseHomeFragment
import com.santiago.battaglino.esp32.presentation.ui.server.ServerViewModel
import com.santiago.battaglino.esp32.util.textChanges
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigFragment : BaseHomeFragment<FragmentConfigBinding>() {

    private val viewModel: ServerViewModel by viewModel()

    override fun observe() {
        viewModel.resultSendConfig.observe(viewLifecycleOwner) {
            it.error?.let {

            }
            it.data?.let {

            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentConfigBinding {
        return FragmentConfigBinding.inflate(inflater, container, false)
    }

    @OptIn(FlowPreview::class)
    override fun setUpViews() {
        super.setUpViews()

        viewBinding?.apply {

            runView.setText(appData.run.toString())
            runView.textChanges().debounce(1000).onEach {
                it?.apply {
                    if (toString().isNotEmpty())
                        appData.run = toString().toInt()
                }
            }.launchIn(lifecycleScope)

            everyView.setText(appData.every.toString())
            everyView.textChanges().debounce(1000).onEach {
                it?.apply {
                    if (toString().isNotEmpty())
                        appData.every = toString().toInt()
                }
            }.launchIn(lifecycleScope)

            running.setChecked(appData.isRunning == 1)
            running.setOnCheckedChangeListener { checked ->
                if (checked) {
                    appData.isRunning = 1
                } else {
                    appData.isRunning = 0
                }
                viewModel.sendConfig()
            }

            deepSleep.setChecked(appData.deepSleep == 1)
            deepSleep.setOnCheckedChangeListener { checked ->
                if (checked) {
                    appData.deepSleep = 1
                } else {
                    appData.deepSleep = 0
                }
            }
        }
    }
}