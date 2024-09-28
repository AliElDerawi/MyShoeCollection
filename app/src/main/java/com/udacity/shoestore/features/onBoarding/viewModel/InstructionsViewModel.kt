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

class InstructionsViewModel(val app: Application) : BaseViewModel(app) {


    private var _currentPageMutableLiveDate = MutableLiveData<Int>(0)
    val currentPagePageLiveData: LiveData<Int>
        get() = _currentPageMutableLiveDate


    private var _lastPageMutableStateFlow = MutableStateFlow(0)
    val lastPageStateFlow: StateFlow<Int>
        get() = _lastPageMutableStateFlow

    private var _goNextScreen = SingleLiveEvent<Boolean>()
    val goNextScreen: LiveData<Boolean>
        get() = _goNextScreen

    private var _instructionList = MutableStateFlow<MutableList<InstructionModel>>(mutableListOf())
    val instructionList: StateFlow<MutableList<InstructionModel>>
        get() = _instructionList

    init {
        _currentPageMutableLiveDate.value = 0
        _instructionList.value.addAll(app.getInstruction())
    }

    fun setCurrentPage(page: Int) {
        _currentPageMutableLiveDate.value = page
    }

    private fun incrementPage() {
        _currentPageMutableLiveDate.value = _currentPageMutableLiveDate.value?.plus(1)
    }

    fun setLastPage(page: Int) {
        _lastPageMutableStateFlow.value = page
    }


    fun onPageChange(position: Int) {

        _currentPageMutableLiveDate.value = position

    }

    fun onNextCardClick() {
        if (currentPagePageLiveData.value == lastPageStateFlow.value) {
            _goNextScreen.value = true
        } else {
            incrementPage()
        }
    }

    fun onSkipClick() {
        _goNextScreen.value = true
    }


}