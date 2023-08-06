package com.santiago.battaglino.esp32.util

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

private const val tag = "Permission"

private fun requestCameraPermission(componentActivity: ComponentActivity): Boolean {
    var granted = false
    val requestCameraPermissionLauncher =
        componentActivity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            granted = if (isGranted) {
                Log.d(tag, "Camera Permission Granted")
                true
            } else {
                Log.d(tag, "Camera Permission Not Granted")
                false
            }
        }

    when {
        ContextCompat.checkSelfPermission(
            componentActivity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {
            Log.d(tag, "Camera Permission Granted")
            granted = true
        }

        componentActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
            Log.d(tag, "Request Permission Rationale")
            granted = false
        }

        else -> {
            Log.d(tag, "Asking For Camera Permission")
            requestCameraPermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }
    return granted
}