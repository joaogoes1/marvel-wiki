package com.joaogoes.marvelwiki.utils

import com.joaogoes.marvelwiki.characters.data.response.CharacterApiResponse
import com.joaogoes.marvelwiki.characters.data.response.CharacterDataContainer
import com.joaogoes.marvelwiki.characters.data.response.CharacterResponse
import com.joaogoes.marvelwiki.characters.data.response.ComicList
import com.joaogoes.marvelwiki.characters.data.response.EventList
import com.joaogoes.marvelwiki.characters.data.response.Image
import com.joaogoes.marvelwiki.characters.data.response.SeriesList
import com.joaogoes.marvelwiki.characters.data.response.StoryList
import com.joaogoes.marvelwiki.characters.data.response.URL


object CharacterApiResponseFactory {
    fun make(
        code: Int = 200,
        data: CharacterDataContainer,
    ) = CharacterApiResponse(
        code = code,
        status = null,
        copyright = null,
        attributionText = null,
        attributionHTML = null,
        data = data,
        etag = null
    )

    fun makeCharacterDataContainer(
        result: List<CharacterResponse>? = null,
    ) = CharacterDataContainer(
        offset = null,
        limit = null,
        total = null,
        count = null,
        results = result
    )

    fun makeCharacterResponse(
        id: Int? = null,
        name: String? = null,
        description: String? = null,
        resourceURI: String? = null,
        urls: List<URL>? = null,
        thumbnailPath: String? = null,
        thumbnailExtension: String? = null,
        comics: ComicList? = null,
        stories: StoryList? = null,
        events: EventList? = null,
        series: SeriesList? = null,
    ) = CharacterResponse(
        id = id,
        name = name,
        description = description,
        resourceURI = resourceURI,
        urls = urls,
        thumbnail = Image(
            path = thumbnailPath,
            extension = thumbnailExtension,
        ),
        comics = comics,
        stories = stories,
        events = events,
        series = series,
    )
}