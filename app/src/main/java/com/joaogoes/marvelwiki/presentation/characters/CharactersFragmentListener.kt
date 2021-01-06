package com.joaogoes.marvelwiki.presentation.characters

import com.joaogoes.marvelwiki.data.model.CharacterModel

interface CharactersFragmentListener {
    fun openCharacter(characterId: Int)
    fun saveFavorite(character: CharacterModel)
}