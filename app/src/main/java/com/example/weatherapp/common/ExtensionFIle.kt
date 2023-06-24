package com.example.weatherapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Context.checkConnection(): Flow<Boolean> = callbackFlow {

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            trySend(true)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            trySend(false)
        }
    }
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerNetworkCallback(
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
        networkCallback
    )
    awaitClose {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

fun EditText.textChange():Flow<Editable?> = callbackFlow {
    val callbacks = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Log.d("textChange", "beforeTextChanged: ")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("textChange", "onTextChanged: ")

        }

        override fun afterTextChanged(s: Editable?) {
            Log.d("textChange", "afterTextChanged: ")

            trySend(s)
        }

    }
    awaitClose {
        removeTextChangedListener(callbacks)
    }
}

fun Context.makeSnackBar(msg: String, view: View) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}