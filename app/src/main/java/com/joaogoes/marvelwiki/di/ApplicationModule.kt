package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ApplicationModule {
    @ContributesAndroidInjector
    fun provideActivity(): MainActivity
}