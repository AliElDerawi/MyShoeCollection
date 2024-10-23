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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel(val app: Application) : BaseViewModel(app) {


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

    val isNewUserFlow: Flow<Boolean> = app.dataStore.data.map { preferences ->
        preferences[AppSharedData.PREF_IS_NEW_USER] ?: true
    }


    init {

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

    fun updateNewUserValidation(isNewUser: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            app.dataStore.edit { preferences ->
                preferences[AppSharedData.PREF_IS_NEW_USER] = isNewUser
            }
        }
    }

}