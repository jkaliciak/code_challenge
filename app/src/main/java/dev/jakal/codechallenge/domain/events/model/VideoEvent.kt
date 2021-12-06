package dev.jakal.codechallenge.domain.events.model

import org.threeten.bp.ZonedDateTime

data class VideoEvent(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    val imageUrl: String,
    val videoUrl: String
)
