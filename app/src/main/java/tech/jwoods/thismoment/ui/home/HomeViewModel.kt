package tech.jwoods.thismoment.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository

class HomeViewModel @ViewModelInject constructor(
    private val momentRepository: MomentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun observeMoments(): LiveData<List<Moment>> {
        return momentRepository.observeMoments()
    }
}