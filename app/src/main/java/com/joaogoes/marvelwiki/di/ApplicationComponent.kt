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
        RepositoryModule::class,
        NetworkModule::class,
        DomainModule::class,
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