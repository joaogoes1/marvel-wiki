package com.joaogoes.marvelwiki.di

import android.app.Application
import android.content.Context
import com.joaogoes.marvelwiki.MarvelWikiApplication
import com.joaogoes.marvelwiki.data.database.MarvelDatabase
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        PresentationModule::class,
        DomainModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        DatabaseModule::class,
        NetworkModule::class,
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
    fun inject(application: MarvelWikiApplication)
}