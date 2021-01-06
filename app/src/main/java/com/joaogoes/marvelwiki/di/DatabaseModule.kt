package com.joaogoes.marvelwiki.di

import android.content.Context
import com.joaogoes.marvelwiki.data.database.MarvelDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): MarvelDatabase = MarvelDatabase.buildDatabase(context)
}