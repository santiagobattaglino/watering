package com.santiago.battaglino.esp32.data.repository

import android.content.SharedPreferences

class SharedPreferenceUtils(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val SHARED_PREF_NAME = "shared_pref"
    }

    fun saveString(key: String, item: String) {
        sharedPreferences.edit().putString(key, item).apply()
    }

    fun saveBoolean(key: String, boolean: Boolean) {
        sharedPreferences.edit().putBoolean(key, boolean).apply()
    }

    fun saveInt(key: String, item: Int) {
        sharedPreferences.edit().putInt(key, item).apply()
    }

    fun getString(key: String, default: String? = null): String? =
        sharedPreferences.getString(key, default)

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, default)

    fun getInt(key: String, default: Int = 0): Int = sharedPreferences.getInt(key, default)

    fun removeString(key: String) = sharedPreferences.edit().remove(key).commit()
}