package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.room.TypeConverter

class RoomConverters {
    @TypeConverter
    fun uriToString(value: Uri?): String? = value?.toString()

    @TypeConverter
    fun stringToUri(value: String?): Uri? = value?.let { Uri.parse(it) }
}
