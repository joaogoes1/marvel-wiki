package com.joaogoes.marvelwiki.characters.navigation

interface CharactersNavigator {

    fun openCharactersList()

    fun openCharacterDetails(characterId: Int)
}