package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class RoomConverters {
    @TypeConverter
    fun uriToString(value: Uri?): String? = value?.toString()

    @TypeConverter
    fun stringToUri(value: String?): Uri? = value?.let { Uri.parse(it) }

    @TypeConverter
    fun zonedDateTimeToLong(value: ZonedDateTime?): Long? = value?.toEpochSecond()

    @TypeConverter
    fun longToZonedDateTime(value: Long?): ZonedDateTime? = value?.let {
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
    }
}
