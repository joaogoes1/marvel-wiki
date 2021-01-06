package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.presentation.MainActivity
import com.joaogoes.marvelwiki.presentation.characters.CharactersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PresentationModule {

    @ContributesAndroidInjector
    fun provideActivity(): MainActivity

    @ContributesAndroidInjector
    fun provideCharactersFragment(): CharactersFragment
}