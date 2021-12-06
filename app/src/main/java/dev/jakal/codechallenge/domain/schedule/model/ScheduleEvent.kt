package dev.jakal.codechallenge.domain.schedule.model

import org.threeten.bp.ZonedDateTime

data class ScheduleEvent(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    val imageUrl: String
)
