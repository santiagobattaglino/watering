package com.santiago.battaglino.esp32.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.santiago.battaglino.esp32.BuildConfig
import com.santiago.battaglino.esp32.data.repository.SharedPreferenceUtils
import com.santiago.battaglino.esp32.domain.model.AppData
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object NetworkUtil {

    fun getLocalIpAddress(): String? {
        try {
            val nwInterfaceList: Enumeration<NetworkInterface> =
                NetworkInterface.getNetworkInterfaces()
            for (nwInterface in nwInterfaceList) {
                val inetAddressList = nwInterface.inetAddresses
                for (inetAddress in inetAddressList) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            Log.e("NetworkUtil", ex.message ?: "")
        }
        return null
    }

    @Suppress("KotlinConstantConditions")
    fun getIpAddressInLocalNetwork(
        context: Context, sp: SharedPreferenceUtils, appData: AppData
    ): String? {
        if (!Constants.AUTO_DETECT_IP) return BuildConfig.SERVER_IP_ADDRESS
        if (BuildConfig.BUILD_TYPE == "emulator") return Constants.SERVER_IP_ADDRESS
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getLinkProperties(connectivityManager.activeNetwork)?.apply {
            //Log.d(tag, "getIpAddressInLocalNetwork getLinkProperties $this")
            linkAddresses.forEach {
                //Log.d(tag, "getIpAddressInLocalNetwork linkAddress $it")
                if (it.address is Inet4Address && !it.address.isLoopbackAddress && it.address.isSiteLocalAddress) {
                    // ?.replaceAfterLast(".", "100")
                    it.address.hostAddress?.apply {
                        sp.saveString(Arguments.SERVER_IP_ADDRESS, this)
                        appData.serverIpAddress = this
                        return this
                    }
                }
            }
        }
        return null
    }

    fun isConnected(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            result = when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true

                else -> false
            }
        }
        return result
    }
}