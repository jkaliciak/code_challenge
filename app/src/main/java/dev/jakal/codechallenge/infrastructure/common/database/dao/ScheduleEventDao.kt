package dev.jakal.codechallenge.infrastructure.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.jakal.codechallenge.infrastructure.common.database.model.ScheduleEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleEventDao {

    @Query("SELECT * FROM schedule_event")
    fun observeAll(): Flow<List<ScheduleEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ScheduleEventEntity>)

    @Query("DELETE FROM schedule_event")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsertAll(entities: List<ScheduleEventEntity>) {
        deleteAll()
        insertAll(entities)
    }
}
