package com.joaogoes.marvelwiki.presentation

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.receiver.InternetStatusBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val connectionBroadcastReceiver = InternetStatusBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observerConnectionState()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectionBroadcastReceiver)
    }

    private fun observerConnectionState() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    Toast.makeText(applicationContext, R.string.no_connection, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
                registerReceiver(connectionBroadcastReceiver, it)
            }
        }
    }
}