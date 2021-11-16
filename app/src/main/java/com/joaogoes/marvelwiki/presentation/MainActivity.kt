package com.joaogoes.marvelwiki.presentation

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.databinding.ActivityMainBinding
import com.joaogoes.marvelwiki.receiver.InternetStatusBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val connectionBroadcastReceiver = InternetStatusBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observerConnectionState()
        binding.homeBottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            )
        )
    }

    override fun onStop() {
        super.onStop()
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P)
            unregisterReceiver(connectionBroadcastReceiver)
    }

    private fun observerConnectionState() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    Toast.makeText(applicationContext, R.string.no_connection, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
                registerReceiver(connectionBroadcastReceiver, it)
            }
        }
    }
}