package com.joaogoes.marvelwiki.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.joaogoes.marvelwiki.R

class InternetStatusBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val hasConnection: Boolean = intent?.getBooleanExtra("status", true) ?: return
        if (!hasConnection)
            Toast.makeText(context, context?.getString(R.string.no_connection), Toast.LENGTH_SHORT).show()
    }
}