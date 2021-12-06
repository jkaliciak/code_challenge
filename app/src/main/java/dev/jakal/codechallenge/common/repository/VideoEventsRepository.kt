package dev.jakal.codechallenge.common.repository

import dev.jakal.codechallenge.domain.events.model.VideoEvent
import kotlinx.coroutines.flow.Flow

interface VideoEventsRepository {

    fun observeVideoEvents(): Flow<List<VideoEvent>>

    suspend fun fetchVideoEvents()
}
