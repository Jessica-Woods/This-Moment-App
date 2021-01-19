package tech.jwoods.thismoment.ui.moment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_moment_cell.view.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.ui.shared.MomentImageView

class MomentAdapter(
    val onClick: (Moment) -> Unit = { _ -> Unit }
) : ListAdapter<Moment, MomentAdapter.ViewHolder>(Moment.diff) {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val momentImageView: MomentImageView = view.momentImage
        private val momentTitle: TextView = view.momentTitle

        fun bind(moment: Moment) {
            momentImageView.setGoldFrame(moment)
            momentImageView.setPhoto(moment.photo)
            momentTitle.text = moment.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_moment_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moment = getItem(position)
        holder.bind(moment)
        holder.itemView.setOnClickListener { onClick(moment) }
    }
}