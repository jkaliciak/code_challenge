package dev.jakal.codechallenge.infrastructure.common.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "schedule_event")
data class ScheduleEventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    @ColumnInfo(name = "image_url") val imageUrl: String
)
