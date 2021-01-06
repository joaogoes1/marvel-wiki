package com.joaogoes.marvelwiki.di

import android.app.Application
import com.joaogoes.marvelwiki.MarvelWikiApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(
    modules = [
        ApplicationModule::class,
        AndroidInjectionModule::class,
        DomainModule::class,
        RepositoryModule::class,
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