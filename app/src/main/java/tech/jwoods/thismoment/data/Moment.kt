package tech.jwoods.thismoment.data

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class Moment (
    val title: String,
    val description: String,
    val date: LocalDate

    // TODO Starred + Image
) : Parcelable {
    companion object {
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