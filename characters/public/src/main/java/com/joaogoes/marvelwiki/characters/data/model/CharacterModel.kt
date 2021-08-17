package com.joaogoes.marvelwiki.characters.data.model

data class CharacterModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<CharacterUrl>,
    val imageUrl: String?,
    val comics: List<ComicModel>,
    val stories: List<StoryModel>,
    val events: List<EventModel>,
    val series: List<SeriesModel>,
    val isFavorite: Boolean = false
)
data class CharacterUrl(
    val type: String?,
    val url: String?,
)

data class ComicModel(
    val resourceURI: String?,
    val name: String?,
)

data class StoryModel(
    val resourceURI: String?,
    val name: String?,
    val type: String?,
)

data class EventModel(
    val resourceURI: String?,
    val name: String?,
)

data class SeriesModel(
    val resourceURI: String?,
    val name: String?,
)
