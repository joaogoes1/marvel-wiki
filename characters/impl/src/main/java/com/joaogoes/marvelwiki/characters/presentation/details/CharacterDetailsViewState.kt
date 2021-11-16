package com.joaogoes.marvelwiki.characters.presentation.details

import com.joaogoes.marvelwiki.characters.data.model.CharacterModel


sealed class CharacterDetailsViewState {
    object Loading : CharacterDetailsViewState()
    data class Success(val characterModel: CharacterModel) : CharacterDetailsViewState()
    object Error : CharacterDetailsViewState()
    object NoConnection : CharacterDetailsViewState()
}