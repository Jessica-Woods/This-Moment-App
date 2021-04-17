package tech.jwoods.thismoment.ui.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_create_edit.*
import kotlinx.android.synthetic.main.layout_album_cell.view.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Album
import kotlin.reflect.KFunction0

class AlbumAdapter(
    val onClick: (Album) -> Unit = { _ -> Unit },
    val onTitleChanged: (Album) -> Unit = { _ -> Unit }
): ListAdapter<Album, AlbumAdapter.ViewHolder>(Album.diff) {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val albumTitle: TextView = view.albumTitle
        val albumImage: ImageView = view.albumImage

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
        holder.albumTitle.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && holder.albumTitle.error == null) {
                onTitleChanged(album.copy(title = holder.albumTitle.text.toString()))
            }
        }
        holder.albumImage.setOnClickListener { onClick(album) }
    }
}
