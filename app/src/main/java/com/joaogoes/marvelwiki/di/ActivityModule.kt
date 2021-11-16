package com.joaogoes.marvelwiki.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun providesFragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
}