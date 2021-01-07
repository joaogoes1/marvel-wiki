package com.joaogoes.marvelwiki.utils

import com.joaogoes.marvelwiki.data.response.*

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