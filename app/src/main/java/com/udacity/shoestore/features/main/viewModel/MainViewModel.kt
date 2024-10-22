package com.udacity.shoestore.features.main.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.models.ShoeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(val app : Application) : BaseViewModel(app) {


    private val _hideToolbar = MutableLiveData<Boolean>()

    val hideToolbar: LiveData<Boolean>
        get() = _hideToolbar

    private val _toolbarTitle = MutableLiveData<String>()

    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private var _shoeList = MutableStateFlow<MutableList<ShoeModel>>(mutableListOf())

    val shoeList: StateFlow<MutableList<ShoeModel>>
        get() = _shoeList

    private val _showUpButton = MutableLiveData<Boolean>()
    val showUpButton: LiveData<Boolean>
        get() = _showUpButton


    init {
        _shoeList.value = mutableListOf()
    }

    fun setHideToolbar(hideToolbar: Boolean) {
        _hideToolbar.value = hideToolbar
    }


    fun setToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun showUpButton(show: Boolean) {
        _showUpButton.value = show
    }

    fun addShoe(shoe: ShoeModel) {
        _shoeList.value.add(shoe)
    }


}