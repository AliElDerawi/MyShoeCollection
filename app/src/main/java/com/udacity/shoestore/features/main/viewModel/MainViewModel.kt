package com.udacity.shoestore.features.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedMethods.notifyObserver

class MainViewModel : ViewModel() {


    private val _hideToolbar = MutableLiveData<Boolean>()

    val hideToolbar: LiveData<Boolean>
        get() = _hideToolbar

    private val _toolbarTitle = MutableLiveData<String>()

    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private var _shoeList = MutableLiveData<MutableList<ShoeModel>>()

    val shoeList: LiveData<MutableList<ShoeModel>>
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
        _shoeList.value!!.add(shoe)
//        _shoeList.notifyObserver()
    }


}