package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "moment")
data class Moment(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val albumId: Long,
    val title: String,
    val description: String,
    val date: ZonedDateTime,
    val photo: Uri?,
    val starred: Boolean
) {
    fun matchesSearchText(text: String): Boolean {
        if (text.isEmpty()) {
            return true
        }

        val words: List<String> = text.split(" ", ",")

        return words.all { word ->
            title.contains(word, ignoreCase = true)
                    || description.contains(word, ignoreCase = true)
        }
    }

    companion object {
        fun empty(): Moment = Moment(0, 0, "", "", ZonedDateTime.now(), null, false)

        val diff = object : DiffUtil.ItemCallback<Moment>() {
            override fun areItemsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                return oldItem == newItem
            }
        }
    }
}