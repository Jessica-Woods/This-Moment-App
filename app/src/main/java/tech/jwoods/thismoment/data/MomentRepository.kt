package tech.jwoods.thismoment.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import javax.inject.Inject

class MomentRepository @Inject constructor(private val momentDao: MomentDAO) {
    fun observeMoments(): LiveData<List<Moment>> {
        return momentDao.observeMoments().distinctUntilChanged()
    }

    fun observeMoment(id: Long): LiveData<Moment> {
        return momentDao.observeMoment(id).distinctUntilChanged()
    }

    suspend fun updatePhoto(id: Long, photoURI: Uri?) {
        momentDao.updatePhoto(id, photoURI)
    }

    suspend fun delete(moment: Moment) {
        momentDao.delete(moment)
    }

    suspend fun save(moment: Moment): Moment {
        val momentId = momentDao.insert(moment)
        return moment.copy(id = momentId)
    }
}