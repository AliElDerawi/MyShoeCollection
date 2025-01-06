package com.udacity.shoestore.features.shoeDetail.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


class ShoeDetailViewModel(val app: Application) : BaseViewModel(app) {

    private var _shoeNameStateFlow = MutableStateFlow("")
    val shoeNameStateFlow: StateFlow<String>
        get() = _shoeNameStateFlow

    private var _shoeCompanyStateFlow = MutableStateFlow("")
    val shoeCompanyStateFlow: StateFlow<String>
        get() = _shoeCompanyStateFlow

    private var _shoeSizeStateFlow = MutableStateFlow("")
    val shoeSizeStateFlow: StateFlow<String>
        get() = _shoeSizeStateFlow

    private var _shoeDescriptionStateFlow = MutableStateFlow("")
    val shoeDescriptionStateFlow: StateFlow<String>
        get() = _shoeDescriptionStateFlow

    private var _onProcessSaveShoeSingleLiveEvent = SingleLiveEvent<ShoeModel>()
    val onProcessSaveShoeSingleLiveEvent: LiveData<ShoeModel>
        get() = _onProcessSaveShoeSingleLiveEvent

    private var _onCancelClickSingleLiveEvent = SingleLiveEvent<Boolean>()
    val onCancelClickSingleLiveEvent: LiveData<Boolean>
        get() = _onCancelClickSingleLiveEvent

    private var _instructionMessageLiveData = MutableLiveData<String>(app.getString(R.string.text_add_shoe_message))
    val instructionMessageLiveData: LiveData<String>
        get() = _instructionMessageLiveData

    init {

    }

    val isSaveButtonEnabledStateFlow: StateFlow<Boolean> = combine(
        _shoeNameStateFlow, _shoeCompanyStateFlow, _shoeSizeStateFlow, _shoeDescriptionStateFlow
    ) { name, company, size, description ->
        name.isNotEmpty() && company.isNotEmpty() && size.isNotEmpty() && description.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onSaveClick() {
        when {
            _shoeNameStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_shoe_name
            _shoeCompanyStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_shoe_company
            _shoeSizeStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_shoe_size
            _shoeDescriptionStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_please_enter_shoe_description
            else -> {
                val shoeModel = ShoeModel(
                    _shoeNameStateFlow.value,
                    _shoeSizeStateFlow.value.toDouble(),
                    _shoeCompanyStateFlow.value,
                    _shoeDescriptionStateFlow.value
                )
                _onProcessSaveShoeSingleLiveEvent.value = shoeModel
            }
        }
    }

    fun onCancelClick() {
        _onCancelClickSingleLiveEvent.value = true
    }

    fun onNameTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeNameStateFlow.value = s.toString()
    }

    fun onCompanyTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeCompanyStateFlow.value = s.toString()
    }

    fun onSizeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeSizeStateFlow.value = s.toString()
    }

    fun onDescriptionTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _shoeDescriptionStateFlow.value = s.toString()
    }

}