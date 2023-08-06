package com.santiago.battaglino.esp32.util

import android.util.Log
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

private const val tag = "Json"

private val gson = Gson()
fun toJsonObject(obj: Any): JSONObject? {
    return try {
        val jsonObject = JSONObject(gson.toJson(obj))
        Log.d(tag, "$jsonObject")
        jsonObject
    } catch (e: JSONException) {
        null
    }
}