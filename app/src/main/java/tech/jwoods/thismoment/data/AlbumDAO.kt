package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlbumDAO {
    @Query("SELECT * FROM album")
    fun observeAlbums(): LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE id = :id")
    fun observeAlbum(id: Long): LiveData<Album>

    @Query("UPDATE album SET cover = :coverURI WHERE id = :id")
    suspend fun updateCover(id: Long, coverURI: Uri?)

    @Delete
    suspend fun delete(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album): Long
}