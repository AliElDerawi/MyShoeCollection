package com.udacity.shoestore.features.login.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.utils.AppSharedMethods.checkIfUserExist
import com.udacity.shoestore.utils.AppSharedMethods.isValidEmail
import com.udacity.shoestore.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(val app: Application) : BaseViewModel(app) {

    private val _completeLoginLiveData = SingleLiveEvent<Boolean>()
    val completeLoginLiveData: LiveData<Boolean>
        get() = _completeLoginLiveData

    private val _emailStateFlow = MutableStateFlow("")
    val emailStateFlow: StateFlow<String>
        get() = _emailStateFlow

    private val _passwordStateFlow = MutableStateFlow("")
    val passwordStateFlow: StateFlow<String>
        get() = _passwordStateFlow

    private val _onCreateAccountClick = SingleLiveEvent<Boolean>()
    val onCreateAccountClick: LiveData<Boolean>
        get() = _onCreateAccountClick

    private val _showEmailError = SingleLiveEvent<Int>()
    val showEmailError: LiveData<Int>
        get() = _showEmailError

    private val _showPasswordError = SingleLiveEvent<Int>()
    val showPasswordError: LiveData<Int>
        get() = _showPasswordError


    fun login() {
        if (_emailStateFlow.value.isEmpty()) {
            _showEmailError.value = R.string.text_msg_please_enter_email
        } else if (!_emailStateFlow.value.isValidEmail()) {
            _showEmailError.value = R.string.text_msg_enter_valid_email_address
        } else if (_passwordStateFlow.value.isEmpty()) {
            _showPasswordError.value = R.string.text_msg_please_enter_password
        } else if (!checkIfUserExist(_emailStateFlow.value, _passwordStateFlow.value)) {
            _showPasswordError.value = R.string.text_msg_invalid_email_or_password
        } else {
            _completeLoginLiveData.value = true
        }
    }

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _emailStateFlow.value = s.toString()
    }

    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _passwordStateFlow.value = s.toString()
    }

    fun onCreateAccountClick() {
        _onCreateAccountClick.value = true
    }

}

