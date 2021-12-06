package dev.jakal.codechallenge.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.infrastructure.common.database.dao.ScheduleEventDao
import dev.jakal.codechallenge.infrastructure.common.database.dao.VideoEventDao
import dev.jakal.codechallenge.infrastructure.common.network.CodeChallengeApi
import dev.jakal.codechallenge.infrastructure.events.repository.VideoEventsRepositoryImpl
import dev.jakal.codechallenge.infrastructure.schedule.repository.ScheduleEventsRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Reusable
    fun videoEventsRepository(
        api: CodeChallengeApi,
        dao: VideoEventDao
    ): VideoEventsRepository = VideoEventsRepositoryImpl(api, dao)

    @Provides
    @Reusable
    fun scheduleEventsRepository(
        api: CodeChallengeApi,
        dao: ScheduleEventDao
    ): ScheduleEventsRepository = ScheduleEventsRepositoryImpl(api, dao)
}
