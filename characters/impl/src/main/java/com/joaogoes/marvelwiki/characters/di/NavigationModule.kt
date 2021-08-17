package com.joaogoes.marvelwiki.characters.di

import com.joaogoes.marvelwiki.characters.navigation.CharactersNavigator
import com.joaogoes.marvelwiki.characters.navigation.CharactersNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

    @Binds
    fun bindsCharactersListNavigator(
        charactersNavigatorImpl: CharactersNavigatorImpl
    ): CharactersNavigator
}