package dev.jakal.codechallenge.infrastructure.common.network.model

import org.threeten.bp.ZonedDateTime

data class ScheduleEventNetwork(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    val imageUrl: String
)
