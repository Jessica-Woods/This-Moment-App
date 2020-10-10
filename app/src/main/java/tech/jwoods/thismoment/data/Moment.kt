package tech.jwoods.thismoment.data

import androidx.recyclerview.widget.DiffUtil
import java.time.LocalDate

data class Moment (
    val title: String,
    val description: String,
    val date: LocalDate

    // TODO Starred + Image
) {
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