package tech.jwoods.thismoment.ui.edit

import android.net.Uri
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository

class EditViewModel @ViewModelInject constructor(
    private val momentRepository: MomentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun observeMoment(momentId: Long): LiveData<Moment> = momentRepository.observeMoment(momentId)

    fun updatePhoto(momentId: Long, photoURI: Uri?) = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.updatePhoto(momentId, photoURI)
    }

    fun delete(moment: Moment) = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.delete(moment)
    }

    fun save(moment: Moment) = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.save(moment)
    }
}