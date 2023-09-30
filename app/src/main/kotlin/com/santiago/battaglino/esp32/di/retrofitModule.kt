package com.santiago.battaglino.esp32.di

import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.santiago.battaglino.esp32.BuildConfig
import com.santiago.battaglino.esp32.data.network.api.Api
import com.santiago.battaglino.esp32.data.repository.SharedPreferenceUtils
import com.santiago.battaglino.esp32.domain.model.AppData
import com.santiago.battaglino.esp32.util.Arguments
import com.santiago.battaglino.esp32.util.Constants
import com.santiago.battaglino.esp32.util.NetworkUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) Constants.REST_API_LOGGING_LEVEL else HttpLoggingInterceptor.Level.NONE
        interceptor
    }

    single {
        GsonBuilder().registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .serializeSpecialFloatingPointValues().create()
    }

    single(named("RetrofitRest")) {
        (get(named("RetrofitRestBuilder")) as Retrofit).create(Api::class.java)
    }

    single(named("OkHttpClient")) {
        //val sp: SharedPreferenceUtils = get()
        OkHttpClient.Builder()
            .connectTimeout(Constants.REST_API_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .callTimeout(Constants.REST_API_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.REST_API_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(Constants.REST_API_TIME_OUT.toLong(), TimeUnit.SECONDS)/*.addInterceptor { chain ->
                val original = chain.request()
                val token = sp.getString(Arguments.AUTH0_ID_TOKEN) ?: ""
                val requestBuilder =
                    original.newBuilder().addHeader("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                chain.proceed(request)
            }*/.addInterceptor(get() as HttpLoggingInterceptor).build()
    }

    single(named("RetrofitRestBuilder")) {
        val appData = get<AppData>()
        val sp: SharedPreferenceUtils = get()

        appData.serverIpAddress = if (BuildConfig.BUILD_TYPE == "emulator") {
            Constants.SERVER_IP_ADDRESS
        } else {
            sp.getString(Arguments.SERVER_IP_ADDRESS) ?: NetworkUtil.getIpAddressInLocalNetwork(
                androidContext(), sp, appData
            ) ?: BuildConfig.SERVER_IP_ADDRESS
        }

        // Using ngrok for emulator, server port not needed
        /*val baseUrl = if (BuildConfig.BUILD_TYPE == "emulator") {
            "https://${BuildConfig.BUILD_TYPE}/"
        } else {
            "http://${appData.serverIpAddress}:${Constants.SERVER_PORT}/"
        }*/

        Retrofit.Builder().baseUrl("http://${BuildConfig.SERVER_IP_ADDRESS}/").addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(get(named("OkHttpClient")) as OkHttpClient).build()
    }
}
