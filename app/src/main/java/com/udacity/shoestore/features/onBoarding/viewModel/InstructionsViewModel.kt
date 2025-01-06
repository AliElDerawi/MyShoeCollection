package com.udacity.shoestore.features.onBoarding.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.models.InstructionModel
import com.udacity.shoestore.utils.AppSharedMethods.getInstruction
import com.udacity.shoestore.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class InstructionsViewModel(val app: Application) : BaseViewModel(app) {

    private var _currentPageLiveData = MutableLiveData<Int>(-1)
    val currentPageLiveData: LiveData<Int>
        get() = _currentPageLiveData

    private var _lastPageStateFlow = MutableStateFlow(0)
    val lastPageStateFlow: StateFlow<Int>
        get() = _lastPageStateFlow

    private var _goNextScreenSingleLiveEvent = SingleLiveEvent<Boolean>()
    val goNextScreenLiveData: LiveData<Boolean>
        get() = _goNextScreenSingleLiveEvent

    private var _instructionListStateFlow = MutableStateFlow<MutableList<InstructionModel>>(mutableListOf())
    val instructionListStateFlow: StateFlow<MutableList<InstructionModel>>
        get() = _instructionListStateFlow

    init {
        _instructionListStateFlow.value.addAll(app.getInstruction())
        setLastPage(instructionListStateFlow.value.size - 1)
    }

    private fun incrementPage() {
        _currentPageLiveData.value = _currentPageLiveData.value?.plus(1)
    }

    fun setLastPage(page: Int) {
        _lastPageStateFlow.value = page
    }

    fun onPageChange(position: Int) {
        if (position == 0 && currentPageLiveData.value != -1) {
            return
        }
        Timber.d("onPageChange: $position")
        _currentPageLiveData.value = position
    }

    fun onNextCardClick() {
        if (currentPageLiveData.value == lastPageStateFlow.value) {
            _goNextScreenSingleLiveEvent.value = true
        } else {
            incrementPage()
        }
    }

    fun onSkipClick() {
        _goNextScreenSingleLiveEvent.value = true
    }

}