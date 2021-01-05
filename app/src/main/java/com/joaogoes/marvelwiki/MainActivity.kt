package com.joaogoes.marvelwiki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joaogoes.marvelwiki.di.Injectable
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}