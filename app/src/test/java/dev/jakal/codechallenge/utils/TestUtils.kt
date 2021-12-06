package dev.jakal.codechallenge

import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import org.threeten.bp.ZonedDateTime

fun mockVideoEvent(id: String, date: ZonedDateTime) = VideoEvent(
    id = id,
    title = "title $id",
    subtitle = "subtitle $id",
    date = ZonedDateTime.now(),
    imageUrl = "image url $id",
    videoUrl = "video url $id"
)

fun mockScheduleEvent(id: String, date: ZonedDateTime) = ScheduleEvent(
    id = id,
    title = "title $id",
    subtitle = "subtitle $id",
    date = ZonedDateTime.now(),
    imageUrl = "image url $id"
)
