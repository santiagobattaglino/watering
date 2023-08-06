package com.santiago.battaglino.esp32.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log

private const val tag = "Screen"

fun getScreenData(context: Context) {
    val density: String
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    when (displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_LOW -> density = "LDPI"
        DisplayMetrics.DENSITY_140 -> density = "LDPI - MDPI"
        DisplayMetrics.DENSITY_MEDIUM -> density = "MDPI"
        DisplayMetrics.DENSITY_180, DisplayMetrics.DENSITY_200, DisplayMetrics.DENSITY_220 -> density =
            "MDPI - HDPI"

        DisplayMetrics.DENSITY_HIGH -> density = "HDPI"
        DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300 -> density =
            "HDPI - XHDPI"

        306 -> density = "306 DPI"
        DisplayMetrics.DENSITY_XHIGH -> density = "XHDPI"
        DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440 -> density =
            "XHDPI - XXHDPI"

        DisplayMetrics.DENSITY_XXHIGH -> density = "XXHDPI"
        DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_600 -> density = "XXHDPI - XXXHDPI"
        DisplayMetrics.DENSITY_XXXHIGH -> density = "XXXHDPI"
        DisplayMetrics.DENSITY_TV -> density = "TVDPI"
        else -> density = "UNKNOWN"
    }
    Log.d(
        tag,
        "screen density: $density, density ${displayMetrics.density}, densityDpi ${displayMetrics.densityDpi}, scaledDensity ${displayMetrics.scaledDensity}"
    )
    Log.d(
        tag,
        "screen width px: ${displayMetrics.widthPixels}, screen height px: ${displayMetrics.heightPixels}"
    )

    val statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeightPx = resources.getDimensionPixelSize(statusBarId)
    Log.d(tag, "statusBarHeightPx: $statusBarHeightPx")

    val navBarId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val navBarHeightPx = resources.getDimensionPixelSize(navBarId)
    Log.d(tag, "navBarHeightPx: $navBarHeightPx")
    Log.d(
        tag,
        "total screen height: ${navBarHeightPx + statusBarHeightPx + displayMetrics.heightPixels}"
    )
    Log.d(tag, "density: $density")
}