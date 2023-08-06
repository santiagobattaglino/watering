package com.santiago.battaglino.esp32.util

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

private const val tag = "Clipboard"

fun textCopyThenPost(
    context: Context,
    label: String,
    textCopied: String,
    isSensitive: Boolean = false
) {
    val clipboardManager =
        getSystemService(context, ClipboardManager::class.java) as ClipboardManager
    val clipData = ClipData.newPlainText(label, textCopied)
    if (isSensitive) {
        clipData.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                description.extras = PersistableBundle().apply {
                    putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                }
            } else {
                description.extras = PersistableBundle().apply {
                    putBoolean("android.content.extra.IS_SENSITIVE", true)
                }
            }
        }
    }
    clipboardManager.setPrimaryClip(clipData)
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) Toast.makeText(
        context, "Copied", Toast.LENGTH_SHORT
    ).show()
}

fun pastePlainText() {
    // https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#PastePlainText
}