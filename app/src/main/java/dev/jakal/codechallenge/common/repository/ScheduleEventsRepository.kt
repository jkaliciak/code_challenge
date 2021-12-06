package dev.jakal.codechallenge.common.repository

import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import kotlinx.coroutines.flow.Flow

interface ScheduleEventsRepository {

    fun observeScheduleEvents(): Flow<List<ScheduleEvent>>

    suspend fun fetchScheduleEvents()
}
