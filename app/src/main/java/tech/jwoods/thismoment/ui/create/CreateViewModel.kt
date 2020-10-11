package tech.jwoods.thismoment.ui.create

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository

class CreateViewModel @ViewModelInject constructor(
    private val momentRepository: MomentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var moment: Moment = Moment.empty()

    fun updateTitle(title: String) {
        moment = moment.copy(title = title)
        save()
    }

    fun updateDescription(description: String) {
        moment = moment.copy(description = description)
        save()
    }

    fun save() = viewModelScope.launch(Dispatchers.IO) {
        val savedMoment = momentRepository.save(moment)
        moment = savedMoment
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        momentRepository.delete(moment)
    }
}