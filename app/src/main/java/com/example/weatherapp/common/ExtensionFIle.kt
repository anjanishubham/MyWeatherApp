package com.example.weatherapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar


/*fun Context.checkConnection(): Flow<Boolean> = callbackFlow {

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            trySend(true)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            trySend(false)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            trySend(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
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
}*/

fun Context.makeSnackBar(msg: String, view: View) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun Context.isNetworkAvailable():Boolean{
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}