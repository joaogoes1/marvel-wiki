package com.joaogoes.marvelwiki.characters.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterApiResponse(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainer?,
    val etag: String?,
)

@JsonClass(generateAdapter = true)
data class CharacterDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterResponse>?
)

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    val id: Int?,
    val name: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<URL>?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val events: EventList?,
    val series: SeriesList?,
)

@JsonClass(generateAdapter = true)
data class URL(
    val type: String?,
    val url: String?,
)

@JsonClass(generateAdapter = true)
data class Image(
    val path: String?,
    val extension: String?,
)

@JsonClass(generateAdapter = true)
data class ComicList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<ComicSummary>?,
)

@JsonClass(generateAdapter = true)
data class ComicSummary(
    val resourceURI: String?,
    val name: String?,
)

@JsonClass(generateAdapter = true)
data class StoryList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<StorySummary>?,
)

@JsonClass(generateAdapter = true)
data class StorySummary(
    val resourceURI: String?,
    val name: String?,
    val type: String?,
)

@JsonClass(generateAdapter = true)
data class EventList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<EventSummary>?,
)

@JsonClass(generateAdapter = true)
data class EventSummary(
    val resourceURI: String?,
    val name: String?,
)

@JsonClass(generateAdapter = true)
data class SeriesList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<SeriesSummary>?,
)

@JsonClass(generateAdapter = true)
data class SeriesSummary(
    val resourceURI: String?,
    val name: String?,
)