package tech.jwoods.thismoment.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class MomentRepository @Inject constructor(private val momentDao: MomentDAO) {
    fun observeMoments(): LiveData<List<Moment>> {
        return momentDao.observeMoments()
    }

    suspend fun save(moment: Moment):Moment {
        val momentId = momentDao.insert(moment)
        return moment.copy(id = momentId)
    }
}