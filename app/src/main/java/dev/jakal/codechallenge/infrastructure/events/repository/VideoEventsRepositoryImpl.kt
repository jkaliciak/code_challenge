package dev.jakal.codechallenge.infrastructure.events.repository

import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.infrastructure.common.database.dao.VideoEventDao
import dev.jakal.codechallenge.infrastructure.common.network.CodeChallengeApi
import dev.jakal.codechallenge.infrastructure.common.toDomain
import dev.jakal.codechallenge.infrastructure.common.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoEventsRepositoryImpl @Inject constructor(
    private val api: CodeChallengeApi,
    private val dao: VideoEventDao
) : VideoEventsRepository {

    override fun observeVideoEvents(): Flow<List<VideoEvent>> = dao.observeAll()
        .map { list -> list.map { it.toDomain() } }

    override suspend fun fetchVideoEvents() = api.getEvents()
        .map { it.toEntity() }
        .run { dao.clearAndInsertAll(this) }
}
