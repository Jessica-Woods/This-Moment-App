package tech.jwoods.thismoment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository

class HomeViewModel : ViewModel() {
    private val momentRepository = MomentRepository()

    fun getMoments(): LiveData<List<Moment>> {
        return MutableLiveData<List<Moment>>().apply {
            value = momentRepository.getMoments()
        }
    }
}