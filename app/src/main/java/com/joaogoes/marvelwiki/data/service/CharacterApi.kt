package com.joaogoes.marvelwiki.data.service

import com.joaogoes.marvelwiki.data.response.CharacterApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/v1/public/characters")
    suspend fun getCharacter(
        @Query("characterId") characterId: Int,
        @Query("limit") limit: Int? = 1,
    ): CharacterApiResponse

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = 20,
    ): CharacterApiResponse
}