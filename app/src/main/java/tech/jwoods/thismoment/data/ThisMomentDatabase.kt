package tech.jwoods.thismoment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Moment::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class ThisMomentDatabase : RoomDatabase() {
    abstract fun momentDao(): MomentDAO
}