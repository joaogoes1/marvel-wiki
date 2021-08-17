package com.joaogoes.marvelwiki.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.joaogoes.marvelwiki.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun providesNavController(activity: FragmentActivity): NavController {
        return activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
            NavHostFragment.findNavController(it)
        } ?: throw IllegalStateException("nav_host_fragment not found")
    }
}
