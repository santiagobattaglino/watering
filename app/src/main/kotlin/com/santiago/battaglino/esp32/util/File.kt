package com.santiago.battaglino.esp32.util

import android.content.Context
import android.content.res.Resources
import android.os.Environment
import android.util.Log
import com.santiago.battaglino.esp32.R
import java.io.*

private const val tag = "File"

fun copyFileFromAssets(context: Context, fileName: String) {
    val assetManager = context.assets
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null
    try {
        inputStream = assetManager.open(fileName)
        val newFileName = File(context.filesDir, fileName)
        outputStream = FileOutputStream(newFileName)
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
        inputStream.close()
        inputStream = null
        outputStream.flush()
        outputStream.close()
        outputStream = null
    } catch (e: Exception) {
        Log.e(tag, "${e.message}")
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                Log.e(tag, "${e.message}")
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close()
            } catch (e: Exception) {
                Log.e(tag, "${e.message}")
            }
        }
    }
}

// copy CSV file from Assets into app data folder.
//        try {
//            assets.list("")?.filter {
//                it.contains(".csv")
//            }?.let {
//                for (assetFile in it) {
//                    if (!File(filesDir, assetFile).exists()) {
//                        copyFileFromAssets(this@ChartActivity, assetFile)
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

@Throws(IOException::class)
fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
    .also {
        if (!it.exists()) {
            it.outputStream().use { cache ->
                context.assets.open(fileName).use { inputStream ->
                    inputStream.copyTo(cache)
                }
            }
        }
    }

fun removeExtension(fileName: String) = File(fileName).nameWithoutExtension

fun changeExtension(fileName: String, newExtension: String) =
    "${File(fileName).nameWithoutExtension}.$newExtension"

fun getOutputDirectory(context: Context, resources: Resources): File {
    val mediaDir = context.getExternalFilesDirs(Environment.DIRECTORY_MOVIES).firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}