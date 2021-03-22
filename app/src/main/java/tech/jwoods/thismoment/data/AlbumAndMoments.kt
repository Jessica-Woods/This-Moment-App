package tech.jwoods.thismoment.data

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumAndMoments(
    @Embedded
    val album: Album,

    @Relation(parentColumn = "id", entityColumn = "albumId")
    val moments: List<Moment> = emptyList()
)
