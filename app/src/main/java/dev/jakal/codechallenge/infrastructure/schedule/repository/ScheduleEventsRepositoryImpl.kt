package dev.jakal.codechallenge.infrastructure.schedule.repository

import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import dev.jakal.codechallenge.infrastructure.common.database.dao.ScheduleEventDao
import dev.jakal.codechallenge.infrastructure.common.network.CodeChallengeApi
import dev.jakal.codechallenge.infrastructure.common.toDomain
import dev.jakal.codechallenge.infrastructure.common.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleEventsRepositoryImpl @Inject constructor(
    private val api: CodeChallengeApi,
    private val dao: ScheduleEventDao
) : ScheduleEventsRepository {

    override fun observeScheduleEvents(): Flow<List<ScheduleEvent>> = dao.observeAll()
        .map { list -> list.map { it.toDomain() } }

    override suspend fun fetchScheduleEvents() = api.getSchedule()
        .map { it.toEntity() }
        .run { dao.clearAndInsertAll(this) }
}
