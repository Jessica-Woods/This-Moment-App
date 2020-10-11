package tech.jwoods.thismoment.dagger

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.jwoods.thismoment.data.MomentDAO
import tech.jwoods.thismoment.data.MomentRepository
import tech.jwoods.thismoment.data.ThisMomentDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(application: Application): ThisMomentDatabase = Room
        .databaseBuilder(application, ThisMomentDatabase::class.java, "this-moment-db")
        .build()

    @Singleton
    @Provides
    fun providesMomentDao(database: ThisMomentDatabase): MomentDAO = database.momentDao()

    @Singleton
    @Provides
    fun providesMomentRepository(momentDao: MomentDAO): MomentRepository =
        MomentRepository(momentDao)
}