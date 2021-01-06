package com.joaogoes.marvelwiki.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.domain.GetCharacterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacter: GetCharacterUseCase
) : ViewModel() {
    val viewState = CharacterDetailsViewState()

    fun loadCharacter(characterId: Int) = viewModelScope.launch {
        viewState.state.postValue(CharacterDetailsViewState.State.LOADING)
        getCharacter(characterId)
            .onSuccess { character ->
                viewState.character.postValue(character)
                viewState.state.postValue(CharacterDetailsViewState.State.SUCCESS)
            }
            .onError { error ->
                if (error is ServiceError.NoConnectionError)
                    viewState.state.postValue(CharacterDetailsViewState.State.NO_CONNECTION)
                else
                    viewState.state.postValue(CharacterDetailsViewState.State.ERROR)
                viewState.character.postValue(null)
            }
    }
}