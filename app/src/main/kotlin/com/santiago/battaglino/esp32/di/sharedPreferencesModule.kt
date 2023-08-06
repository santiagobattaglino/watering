package com.santiago.battaglino.esp32.di

import android.content.Context.MODE_PRIVATE
import com.santiago.battaglino.esp32.data.repository.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        SharedPreferenceUtils(
            androidContext().getSharedPreferences(
                SharedPreferenceUtils.SHARED_PREF_NAME, MODE_PRIVATE
            )
        )
    }
}