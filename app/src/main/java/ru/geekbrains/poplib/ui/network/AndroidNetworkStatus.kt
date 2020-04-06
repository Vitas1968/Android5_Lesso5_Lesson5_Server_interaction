package ru.geekbrains.poplib.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus

class AndroidNetworkStatus(val context: Context) : NetworkStatus {

    private val statusSubject = BehaviorSubject.create<Boolean>()



    init {
        statusSubject.onNext(false)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()

        connectivityManager.registerNetworkCallback(builder.build(), object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                statusSubject.onNext(true)
            }

            override fun onLost(network: Network) {
                statusSubject.onNext(false)
            }

            override fun onUnavailable() {
                statusSubject.onNext(false)
            }
        })
    }



    override fun isOnline() = statusSubject

    override fun isOnlineSingle() = statusSubject.first(false)
}