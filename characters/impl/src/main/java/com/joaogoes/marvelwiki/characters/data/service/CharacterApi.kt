package com.joaogoes.marvelwiki.characters.data.service

import com.joaogoes.marvelwiki.characters.data.response.CharacterApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int,
    ): CharacterApiResponse

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = 100,
    ): CharacterApiResponse
}