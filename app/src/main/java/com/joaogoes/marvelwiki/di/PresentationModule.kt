package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.presentation.MainActivity
import com.joaogoes.marvelwiki.presentation.characters.CharactersFragment
import com.joaogoes.marvelwiki.presentation.details.CharacterDetailsFragment
import com.joaogoes.marvelwiki.presentation.favorites.FavoritesFragment
import com.joaogoes.marvelwiki.presentation.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PresentationModule {

    @ContributesAndroidInjector
    fun provideActivity(): MainActivity

    @ContributesAndroidInjector
    fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun provideCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    fun provideCharacterDetailsFragment(): CharacterDetailsFragment

    @ContributesAndroidInjector
    fun provideFavoritesFragment(): FavoritesFragment
}