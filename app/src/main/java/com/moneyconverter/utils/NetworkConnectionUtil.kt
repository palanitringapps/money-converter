package com.moneyconverter.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkConnectionUtil {

    const val CONNECTION_ERROR = "connectionError"

    /**
     * @return true when network is connected
     * */
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnectedOrConnecting
    }
}