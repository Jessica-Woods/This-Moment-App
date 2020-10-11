package tech.jwoods.thismoment.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Moment::class], version = 1, exportSchema = false)
abstract class ThisMomentDatabase : RoomDatabase() {
    abstract fun momentDao(): MomentDAO
}