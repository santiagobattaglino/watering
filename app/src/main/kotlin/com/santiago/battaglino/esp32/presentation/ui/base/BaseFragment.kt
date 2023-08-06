package com.santiago.battaglino.esp32.presentation.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.santiago.battaglino.esp32.BuildConfig
import com.santiago.battaglino.esp32.data.network.error.ErrorObject
import com.santiago.battaglino.esp32.domain.model.AppData
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    protected val appData: AppData by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observe()
    }

    internal fun handleError(tag: String, error: ErrorObject) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, error.toString())
        }
    }

    abstract fun setUpViews()
    abstract fun observe()
}