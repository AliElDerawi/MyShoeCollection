package com.udacity.shoestore.features.onBoarding.viewModel

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.SingleLiveEvent

class InstructionsViewModel(val app: Application) : BaseViewModel(app) {


    private var _currentPageMutableLiveDate = MutableLiveData<Int>(0)
    val currentPagePageLiveData: LiveData<Int>
        get() = _currentPageMutableLiveDate


    private var _lastPageMutableLiveData = MutableLiveData<Int>(0)
    val lastPageLiveData: LiveData<Int>
        get() = _lastPageMutableLiveData

    private var _goNextScreen = SingleLiveEvent<Boolean>()
    val goNextScreen: LiveData<Boolean>
        get() = _goNextScreen

    fun init() {
        _currentPageMutableLiveDate.value = 0
        _lastPageMutableLiveData.value = 0
    }

    fun setCurrentPage(page: Int) {
        _currentPageMutableLiveDate.value = page
    }

    private fun incrementPage() {
        _currentPageMutableLiveDate.value = _currentPageMutableLiveDate.value?.plus(1)
    }

    fun setLastPage(page: Int) {
        _lastPageMutableLiveData.value = page
    }


    fun onPageChange(position: Int) {

        _currentPageMutableLiveDate.value = position

    }

    fun onNextCardClick() {
        if (currentPagePageLiveData.value == lastPageLiveData.value) {
            _goNextScreen.value = true
        } else {
            incrementPage()
        }
    }

    fun onSkipClick() {
        _goNextScreen.value = true
    }


}