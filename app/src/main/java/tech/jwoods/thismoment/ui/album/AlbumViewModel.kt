package tech.jwoods.thismoment.ui.album

import android.provider.MediaStore
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jwoods.thismoment.data.Album
import tech.jwoods.thismoment.data.AlbumRepository
import tech.jwoods.thismoment.data.MomentRepository

class AlbumViewModel @ViewModelInject constructor(
    private val albumRepository: AlbumRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun observeAlbums(): LiveData<List<Album>> {
        return albumRepository.observeAlbums()
    }

    fun createNewAlbum(callback: (Album) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val album = albumRepository.save(Album.empty())
        callback(album)
    }
}
