package tech.jwoods.thismoment.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_album_cell.view.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Album

class AlbumAdapter(
    val onClick: (Album) -> Unit = { _ -> Unit }
): ListAdapter<Album, AlbumAdapter.ViewHolder>(Album.diff) {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val albumTitle: TextView = view.albumTitle

        fun bind(album: Album) {
            albumTitle.text = album.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_album_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.ViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
        holder.itemView.setOnClickListener { onClick(album) }
    }
}
