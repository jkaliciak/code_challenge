package dev.jakal.codechallenge.infrastructure.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.jakal.codechallenge.BuildConfig
import dev.jakal.codechallenge.infrastructure.common.database.dao.ScheduleEventDao
import dev.jakal.codechallenge.infrastructure.common.database.dao.VideoEventDao
import dev.jakal.codechallenge.infrastructure.common.database.model.ScheduleEventEntity
import dev.jakal.codechallenge.infrastructure.common.database.model.VideoEventEntity

@Database(
    entities = [
        VideoEventEntity::class,
        ScheduleEventEntity::class
    ],
    version = BuildConfig.DB_VERSION,
    exportSchema = true
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class CodeChallengeDatabase : RoomDatabase() {

    abstract fun videoEventDao(): VideoEventDao

    abstract fun scheduleEventDao(): ScheduleEventDao
}
