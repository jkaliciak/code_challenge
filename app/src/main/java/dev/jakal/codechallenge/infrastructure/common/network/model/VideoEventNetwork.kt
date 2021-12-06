package dev.jakal.codechallenge.infrastructure.common.network.model

import org.threeten.bp.ZonedDateTime

data class VideoEventNetwork(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    val imageUrl: String,
    val videoUrl: String
)
