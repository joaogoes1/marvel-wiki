package com.joaogoes.marvelwiki.data.api

import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/v1/public/characters")
    suspend fun getCharactersAsync(
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = 20,
    ): CharacterApiResponse
}