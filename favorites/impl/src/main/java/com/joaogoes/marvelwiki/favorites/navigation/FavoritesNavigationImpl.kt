package com.joaogoes.marvelwiki.favorites.navigation

import androidx.navigation.NavController
import com.joaogoes.favorites.R
import javax.inject.Inject


class FavoritesNavigationImpl @Inject constructor(
    private val navController: NavController
) : FavoritesNavigation {

    override fun openFavoritesList() {
        navController.navigate(R.id.open_favorites_fragment)
    }
}
