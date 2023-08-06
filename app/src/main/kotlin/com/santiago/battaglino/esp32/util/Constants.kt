package com.santiago.battaglino.esp32.util

import android.util.Range
import okhttp3.logging.HttpLoggingInterceptor

object Constants {

    // DB
    const val DB_NAME = "db"
    const val DB_VERSION = 1
    const val DB_CLEAR_ON_START = false

    // Server and MQTT
    const val REST_API_TIME_OUT = 120 // seconds
    val REST_API_LOGGING_LEVEL =
        HttpLoggingInterceptor.Level.BASIC // On dev builds, if this is set to BODY, file transfer will not work because of long body request / response
    const val SERVER_PORT = 13276
    const val MQTT_BROKER_PORT = 1883
    const val MQTT_TOPIC = "ns/topic"
    const val MQTT_CLIENT_TIMEOUT = 45 // seconds
    const val MQTT_CLIENT_KEEP_ALIVE = 15 // seconds
    const val SERVER_STATUS_PING_INTERVAL = 60000L // 1 min

    // Only for emulator build variant. Remember to forward first:
    // adb -s emulator-5554 forward tcp:1883 tcp:1883
    // adb -s emulator-5554 forward tcp:13276 tcp:13276
    // to be used with ngrok and emulator build type. Eg: 15936 (tcp://0.tcp.sa.ngrok.io:19376)
    const val MQTT_BROKER_NGROK_PORT = 15712

    // to be used with ngrok and emulator build type. Eg: 3fac-45-163-140-141.sa.ngrok.io
    const val SERVER_IP_ADDRESS = "bcd9-45-163-140-140.ngrok-free.app"

    // MEDIA
    const val IMAGES_RESIZE_SIZE = 480
    const val IMAGES_RESIZE_JPG_QUALITY = 70
    const val UPLOAD_VIDEO_SIZE_LIMIT = 100 // pick from gallery
    val VIDEO_FPS = Range(30, 30)

    // VIDEO COMPRESSION
    const val ENABLE_VIDEO_COMPRESSION = false
    const val DELETE_ORIGINAL_VIDEO_AFTER_COMPRESSING = true
    const val UPLOAD_VIDEO_BITRATE = 500000
    const val VIDEO_WIDTH = 426 // Aspect Ration 16:9 426 / 1,77777 = 240 portrait
    const val VIDEO_HEIGHT = 240

    // SHIMMER
    const val SAMPLING_RATE = 51.2
    const val REAL_TIME_SECONDS = 6
    const val DESIRED_BUCKETS = 100000

    // General Config
    const val AUTO_DETECT_IP = true
    const val DELETE_REMOTE_SESSIONS = false

    // Python
    const val USE_TEST_SAMPLE_FILE = false

    // Scores Thresholds
    // TODO hardcoded ranges for now
    const val ANX_1 = 0.000548782
    const val ANX_2 = 0.022129696
    const val VIG_1 = 0.0396049
    const val VIG_2 = 0.3427366
    const val REC_1 = -0.003121
    const val REC_2 = 0.0117985
    const val PERF_1 = 0.3539373 // stress regulation
    const val PERF_2 = 1.428514 // stress regulation
}