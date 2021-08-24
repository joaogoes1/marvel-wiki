package com.joaogoes.marvelwiki.characters.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.joaogoes.marvelwiki.characters.R
import javax.inject.Inject


class CharactersNavigatorImpl @Inject constructor(
    private val navController: NavController
) : CharactersNavigator {

    override fun openCharactersList() {
        navController.navigate(R.id.characters_list)
    }

    override fun openCharacterDetails(characterId: Int) {
        if (navController.currentDestination == null || navController.currentDestination?.id == navController.graph.startDestination) {
            navController.navigate(
                R.id.open_character_details_fragment,
                Bundle().apply { putInt("characterId", characterId) }
            )
        }
    }
}