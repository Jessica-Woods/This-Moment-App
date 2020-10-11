package tech.jwoods.thismoment.ui.edit

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository

class EditViewModel @ViewModelInject constructor(
    private val momentRepository: MomentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun observeMoment(attractionId: Long): LiveData<Moment> {
        return momentRepository.observeMoment(attractionId)
    }

    fun delete(moment: Moment) = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.delete(moment)
    }

    fun save(moment: Moment) = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.save(moment)
    }
}