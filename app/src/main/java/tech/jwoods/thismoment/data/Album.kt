package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class Album(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val cover: Uri?,
    val starred: Boolean
) {
    fun matchesSearchText(text: String): Boolean {
        if (text.isEmpty()) { return true }

        val words: List<String> = text.split(" ", ",")

        return words.all { word -> title.contains(word, ignoreCase = true) }
    }

    companion object {
        fun empty(): Album = Album(0, "New Album", null, false)

        val diff = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem == newItem
            }
        }
    }
}