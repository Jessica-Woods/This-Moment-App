package tech.jwoods.thismoment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MomentDAO {
    @Query("SELECT * FROM moment")
    fun observeMoments(): LiveData<List<Moment>>

    @Query("SELECT * FROM moment WHERE id = :id")
    fun observeMoment(id: Long): LiveData<Moment>

    @Delete
    suspend fun delete(moment: Moment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moment: Moment): Long
}