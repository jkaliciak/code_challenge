package dev.jakal.codechallenge.infrastructure.common

import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import dev.jakal.codechallenge.infrastructure.common.database.model.ScheduleEventEntity
import dev.jakal.codechallenge.infrastructure.common.database.model.VideoEventEntity
import dev.jakal.codechallenge.infrastructure.common.network.model.ScheduleEventNetwork
import dev.jakal.codechallenge.infrastructure.common.network.model.VideoEventNetwork


fun VideoEventEntity.toDomain() = VideoEvent(
    id = id,
    title = title,
    subtitle = subtitle,
    date = date,
    imageUrl = imageUrl,
    videoUrl = videoUrl
)

fun ScheduleEventEntity.toDomain() = ScheduleEvent(
    id = id,
    title = title,
    subtitle = subtitle,
    date = date,
    imageUrl = imageUrl
)

fun VideoEventNetwork.toEntity() = VideoEventEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    date = date,
    imageUrl = imageUrl,
    videoUrl = videoUrl
)

fun ScheduleEventNetwork.toEntity() = ScheduleEventEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    date = date,
    imageUrl = imageUrl
)
