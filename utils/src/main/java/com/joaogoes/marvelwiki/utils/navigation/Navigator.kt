package com.joaogoes.marvelwiki.utils.navigation

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

interface Navigator {
    fun navigate(navGraphId: Int)
}

class NavigatorImpl(
    private val supportFragmentManager: FragmentManager,
    private val navController: NavController
) : Navigator {

    override fun navigate(navGraphId: Int) {
        val finalHost = NavHostFragment.create(navGraphId)
        val bla = navController.graph.startDestinationId
        supportFragmentManager.beginTransaction()
            .replace(bla, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()
    }
}