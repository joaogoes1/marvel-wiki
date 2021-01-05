package com.joaogoes.marvelwiki.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.di.Injectable

class MainActivity : AppCompatActivity(), Injectable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}