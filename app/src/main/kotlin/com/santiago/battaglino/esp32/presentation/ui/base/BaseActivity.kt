package com.santiago.battaglino.esp32.presentation.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.santiago.battaglino.esp32.BuildConfig
import com.santiago.battaglino.esp32.data.network.error.ErrorObject
import com.santiago.battaglino.esp32.domain.model.AppData
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    protected val appData: AppData by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    internal fun handleError(tag: String, error: ErrorObject) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, error.toString())
        }
    }

    abstract fun observe()
}