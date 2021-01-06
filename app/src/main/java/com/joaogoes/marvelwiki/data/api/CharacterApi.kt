package com.joaogoes.marvelwiki.data.api

import com.joaogoes.marvelwiki.data.model.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/v1/public/characters")
    suspend fun getCharactersAsync(
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = 20,
    ): CharacterResponse
}