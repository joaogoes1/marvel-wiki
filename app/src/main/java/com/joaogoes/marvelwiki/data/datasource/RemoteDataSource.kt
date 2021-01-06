package com.joaogoes.marvelwiki.data.datasource

import com.joaogoes.marvelwiki.data.Result
import com.joaogoes.marvelwiki.data.api.CharacterApi
import com.joaogoes.marvelwiki.data.model.CharacterModel
import com.joaogoes.marvelwiki.data.repository.ServiceError
import com.joaogoes.marvelwiki.data.repository.toCharacterModelList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError>
}

class RemoteDataSourceImpl @Inject constructor(
    private val characterApi: CharacterApi
) : RemoteDataSource{

    override suspend fun getCharacters(): Result<List<CharacterModel>, ServiceError> =
        withContext(Dispatchers.IO) {
            val result = characterApi.getCharactersAsync()
            Result.Success(result.toCharacterModelList())
        }
}