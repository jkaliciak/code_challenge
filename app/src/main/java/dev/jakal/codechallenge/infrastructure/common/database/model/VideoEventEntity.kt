package dev.jakal.codechallenge.infrastructure.common.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "video_event")
data class VideoEventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val date: ZonedDateTime,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "video_url") val videoUrl: String
)
