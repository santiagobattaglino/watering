package com.santiago.battaglino.esp32.util

import android.content.res.Resources
import android.util.Log

private const val tag = "Resource"

private fun getResourceId(
    pVariableName: String?,
    pResourcename: String?,
    pPackageName: String?,
    res: Resources
): Int {
    return try {
        res.getIdentifier(pVariableName, pResourcename, pPackageName)
    } catch (e: Exception) {
        Log.e(tag, "${e.message}")
        -1
    }
}