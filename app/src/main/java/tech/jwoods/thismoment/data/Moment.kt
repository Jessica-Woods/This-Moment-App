package tech.jwoods.thismoment.data

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "moment")
@Parcelize
data class Moment(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String

    // TODO Starred + Image
) : Parcelable {
    companion object {
        fun empty(): Moment = Moment(0, "", "")

        val diff = object : DiffUtil.ItemCallback<Moment>() {
            override fun areItemsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                return oldItem == newItem
            }
        }
    }
}