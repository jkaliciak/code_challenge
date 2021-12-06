package dev.jakal.codechallenge.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jakal.codechallenge.infrastructure.common.database.CodeChallengeDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun roomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CodeChallengeDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideVideoEventDao(database: CodeChallengeDatabase) = database.videoEventDao()

    @Singleton
    @Provides
    fun provideScheduleEventDao(database: CodeChallengeDatabase) = database.scheduleEventDao()

    companion object {
        private const val DATABASE_NAME = "code-challenge-db"
    }
}
