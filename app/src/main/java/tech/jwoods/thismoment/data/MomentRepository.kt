package tech.jwoods.thismoment.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class MomentRepository @Inject constructor(private val momentDao: MomentDAO) {
    fun observeMoments(): LiveData<List<Moment>> {
        return momentDao.observeMoments()
    }
}