package tech.jwoods.thismoment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MomentDAO {
    @Query("SELECT * FROM moment")
    fun observeMoments(): LiveData<List<Moment>>

    @Query("SELECT * FROM moment WHERE id = :id")
    fun observeMoment(id: Int): LiveData<List<Moment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moment: Moment)
}