package com.joaogoes.marvelwiki.characters.navigation

import androidx.navigation.NavController
import com.joaogoes.marvelwiki.characters.presentation.characterslist.CharactersFragment
import javax.inject.Inject


class CharactersNavigatorImpl @Inject constructor(
    private val navController: NavController
) : CharactersNavigator {

    override fun openCharactersList() {
        TODO("Not yet implemented")
    }

    override fun openCharacterDetails(characterId: Int) {
//        val action = CharactersFragmentDirections.openCharacterDetailsFragment()
    }
}