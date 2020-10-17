package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MomentDAO {
    @Query("SELECT * FROM moment")
    fun observeMoments(): LiveData<List<Moment>>

    @Query("SELECT * FROM moment WHERE id = :id")
    fun observeMoment(id: Long): LiveData<Moment>

    @Query("UPDATE moment SET photo = :photoURI WHERE id = :id")
    suspend fun updatePhoto(id: Long, photoURI: Uri?)

    @Delete
    suspend fun delete(moment: Moment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moment: Moment): Long
}