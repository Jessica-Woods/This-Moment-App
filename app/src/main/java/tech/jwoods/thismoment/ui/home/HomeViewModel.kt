package tech.jwoods.thismoment.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.data.MomentRepository
import tech.jwoods.thismoment.extensions.combineWith

class HomeViewModel @ViewModelInject constructor(
    private val momentRepository: MomentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val showSearchBar: MutableLiveData<Boolean> = MutableLiveData(false)
    private val useStarFilter: MutableLiveData<Boolean> = MutableLiveData(false)
    private val searchFilter: MutableLiveData<String> = MutableLiveData("")

    fun observeMoments(): LiveData<List<Moment>> {
        return momentRepository.observeMoments()
            .combineWith(useStarFilter, searchFilter) { moments, useStarFilterValue, searchText ->
                val starFilter = if(useStarFilterValue == true) {
                    { moment: Moment  -> moment.starred }
                } else {
                    { true }
                }

                val searchText = searchText ?: ""
                val searchFilter = if (searchText != "") {
                    { moment: Moment -> moment.matchesSearchText(searchText) }
                } else {
                    { true }
                }

                moments
                    ?.filter(starFilter)
                    ?.filter(searchFilter)
                    ?: listOf()
            }

        return momentRepository.observeMoments()
    }

    fun observeStarFilter(): LiveData<Boolean> = useStarFilter
    fun observeShowSearchBar(): LiveData<Boolean> = showSearchBar

    fun toggleSearchBar() {
        showSearchBar.value = showSearchBar.value?.let { !it }
    }

    fun toggleStarFilter() {
        useStarFilter.value = useStarFilter.value?.let { !it }
    }

    fun searchMoments(text: String) {
        searchFilter.value = text
    }
}