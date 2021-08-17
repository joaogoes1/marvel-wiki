package com.joaogoes.marvelwiki.characters.presentation.characterslist

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel


interface CharactersFragmentListener {
    fun openCharacter(characterId: Int)
    fun saveFavorite(character: CharacterModel)
}