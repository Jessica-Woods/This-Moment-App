package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import javax.inject.Inject

class AlbumRepository @Inject constructor(private val albumDao: AlbumDAO) {
    fun observeAlbums(): LiveData<List<Album>> {
        return albumDao.observeAlbums().distinctUntilChanged()
    }

    fun observeAlbum(id: Long): LiveData<Album> {
        return albumDao.observeAlbum(id).distinctUntilChanged()
    }

    suspend fun updateCover(id: Long, coverURI: Uri?) {
        albumDao.updateCover(id, coverURI)
    }

    suspend fun delete(album: Album) {
        albumDao.delete(album)
    }

    suspend fun save(album: Album): Album {
        val albumId = albumDao.insert(album)
        return album.copy(id = albumId)
    }
}