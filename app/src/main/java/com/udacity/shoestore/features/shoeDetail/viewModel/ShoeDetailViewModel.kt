package com.udacity.shoestore.features.shoeDetail.view

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedMethods
import com.udacity.shoestore.utils.ShoeStoreApp


class ShoeDetailViewModel(val app: Application) : BaseViewModel(app) {

    private var _shoeNameLiveData = MutableLiveData<String>("")
    val shoeNameLiveData: MutableLiveData<String>
        get() = _shoeNameLiveData

    private var _shoeCompanyLiveData = MutableLiveData<String>("")
    val shoeCompanyLiveData: MutableLiveData<String>
        get() = _shoeCompanyLiveData

    private var _shoeSizeLiveData = MutableLiveData<String>("")
    val shoeSizeLiveData: MutableLiveData<String>
        get() = _shoeSizeLiveData

    private var _shoeDescriptionLiveData = MutableLiveData<String>("")
    val shoeDescriptionLiveData: MutableLiveData<String>
        get() = _shoeDescriptionLiveData

    private var _onProcessSaveShoe = MutableLiveData<ShoeModel>()

    val onProcessSaveShoe: MutableLiveData<ShoeModel>
        get() = _onProcessSaveShoe

    private var _onCancelClick = MutableLiveData<Boolean>()
    val onCancelClick: MutableLiveData<Boolean>
        get() = _onCancelClick

    private var _instructionMessage = MutableLiveData<String>()
    val instructionMessage: MutableLiveData<String>
        get() = _instructionMessage

    fun onSaveClick() {

        if (_shoeNameLiveData.value!!.isEmpty()) {
            AppSharedMethods.showToast(R.string.text_msg_please_enter_shoe_name)
        } else if (_shoeCompanyLiveData.value!!.isEmpty()) {
            AppSharedMethods.showToast(R.string.text_msg_please_enter_shoe_company)
        } else if (_shoeSizeLiveData.value!!.isEmpty()) {
            AppSharedMethods.showToast(R.string.please_enter_shoe_size)
        } else if (_shoeDescriptionLiveData.value!!.isEmpty()) {
            AppSharedMethods.showToast(R.string.text_please_enter_shoe_description)
        } else {
            val shoeModel = ShoeModel(
                _shoeNameLiveData.value!!,
                _shoeSizeLiveData.value!!.toDouble(),
                _shoeCompanyLiveData.value!!,
                _shoeDescriptionLiveData.value!!
            )
            _onProcessSaveShoe.value = shoeModel
        }

    }

    init {
        _instructionMessage.value =
            ShoeStoreApp.getApp()!!.getString(R.string.text_add_shoe_message)
    }

    fun onCancelClick() {
        _onCancelClick.value = true
    }

    fun onNameTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeNameLiveData.value = s.toString()
    }

    fun onCompanyTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeCompanyLiveData.value = s.toString()
    }

    fun onSizeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeSizeLiveData.value = s.toString()
    }

    fun onDescriptionTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeDescriptionLiveData.value = s.toString()
    }

}