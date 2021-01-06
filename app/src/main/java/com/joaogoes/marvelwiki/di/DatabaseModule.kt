package com.joaogoes.marvelwiki.di

import android.content.Context
import com.joaogoes.marvelwiki.data.database.MarvelDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DatabaseModule {

    @Provides
    @Singleton
    fun database(context: Context): MarvelDatabase = MarvelDatabase.buildDatabase(context)
}