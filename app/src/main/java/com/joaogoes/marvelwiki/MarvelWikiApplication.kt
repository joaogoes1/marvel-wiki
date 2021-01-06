package com.joaogoes.marvelwiki

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.joaogoes.marvelwiki.data.database.MarvelDatabase
import com.joaogoes.marvelwiki.di.DaggerApplicationComponent
import com.joaogoes.marvelwiki.di.DatabaseModule
import com.joaogoes.marvelwiki.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MarvelWikiApplication : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        configureDagger()
    }

    private fun configureDagger() {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is Injectable) {
                    AndroidInjection.inject(activity)
                    (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                        object :
                            FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentPreCreated(
                                fm: FragmentManager,
                                fragment: Fragment,
                                savedInstanceState: Bundle?
                            ) {
                                super.onFragmentPreCreated(fm, fragment, savedInstanceState)
                                if (fragment is Injectable) {
                                    AndroidSupportInjection.inject(fragment)
                                }
                            }
                        }, true
                    )
                }
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}