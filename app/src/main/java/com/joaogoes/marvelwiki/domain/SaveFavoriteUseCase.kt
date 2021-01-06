package com.joaogoes.marvelwiki.domain

import com.joaogoes.marvelwiki.data.database.entity.CharacterEntity

interface SaveFavoriteUseCase {
    suspend operator fun invoke(characterEntity: CharacterEntity)
}