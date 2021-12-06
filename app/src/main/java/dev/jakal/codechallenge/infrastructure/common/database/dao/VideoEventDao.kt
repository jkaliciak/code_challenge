package dev.jakal.codechallenge.infrastructure.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import dev.jakal.codechallenge.infrastructure.common.database.model.VideoEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoEventDao {

    @Query("SELECT * FROM video_event")
    fun observeAll(): Flow<List<VideoEventEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(entities: List<VideoEventEntity>)

    @Query("DELETE FROM video_event")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsertAll(entities: List<VideoEventEntity>) {
        deleteAll()
        insertAll(entities)
    }
}
