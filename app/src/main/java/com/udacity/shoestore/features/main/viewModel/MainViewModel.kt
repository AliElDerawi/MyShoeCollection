package com.udacity.shoestore.features.main.viewModel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(val app: Application) : BaseViewModel(app) {

    private var _hideToolbarLiveData = MutableLiveData<Boolean>()
    val hideToolbarLiveData: LiveData<Boolean>
        get() = _hideToolbarLiveData

    private var _toolbarTitleLiveData = MutableLiveData<String>()
    val toolbarTitleLiveData: LiveData<String>
        get() = _toolbarTitleLiveData

    private var _shoeListStateFlow = MutableStateFlow<MutableList<ShoeModel>>(mutableListOf())
    val shoeListStateFlow: StateFlow<MutableList<ShoeModel>>
        get() = _shoeListStateFlow

    private var _showUpButtonLiveData = MutableLiveData<Boolean>()
    val showUpButtonLiveData: LiveData<Boolean>
        get() = _showUpButtonLiveData

    val isNewUserFlow: Flow<Boolean> = app.dataStore.data.map { preferences ->
        preferences[AppSharedData.PREF_IS_NEW_USER] ?: true
    }

    init {

    }

    fun setHideToolbar(hideToolbar: Boolean) {
        _hideToolbarLiveData.value = hideToolbar
    }

    fun setToolbarTitle(title: String) {
        _toolbarTitleLiveData.value = title
    }

    fun showUpButton(show: Boolean) {
        _showUpButtonLiveData.value = show
    }

    fun addShoe(shoe: ShoeModel) {
        _shoeListStateFlow.value.add(shoe)
    }

    fun updateNewUserValidation(isNewUser: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            app.dataStore.edit { preferences ->
                preferences[AppSharedData.PREF_IS_NEW_USER] = isNewUser
            }
        }
    }

}