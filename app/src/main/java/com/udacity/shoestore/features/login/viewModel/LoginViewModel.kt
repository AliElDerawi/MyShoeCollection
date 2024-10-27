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

    private var _completeLoginSingleLiveEvent = SingleLiveEvent<Boolean>()
    val completeLoginLiveData: LiveData<Boolean>
        get() = _completeLoginSingleLiveEvent

    private var _emailStateFlow = MutableStateFlow("")
    val emailStateFlow: StateFlow<String>
        get() = _emailStateFlow

    private var _passwordStateFlow = MutableStateFlow("")
    val passwordStateFlow: StateFlow<String>
        get() = _passwordStateFlow

    private var _onCreateAccountClickSingleLiveEvent = SingleLiveEvent<Boolean>()
    val onCreateAccountClickLiveData: LiveData<Boolean>
        get() = _onCreateAccountClickSingleLiveEvent

    private var _showEmailErrorSingleLiveEvent = SingleLiveEvent<Int>()
    val showEmailErrorLiveData: LiveData<Int>
        get() = _showEmailErrorSingleLiveEvent

    private var _showPasswordErrorSingleLiveEvent = SingleLiveEvent<Int>()
    val showPasswordErrorLiveData: LiveData<Int>
        get() = _showPasswordErrorSingleLiveEvent


    fun login() {
        when {
            _emailStateFlow.value.isEmpty() -> _showEmailErrorSingleLiveEvent.value = R.string.text_msg_please_enter_email
            !_emailStateFlow.value.isValidEmail() -> _showEmailErrorSingleLiveEvent.value = R.string.text_msg_enter_valid_email_address
            _passwordStateFlow.value.isEmpty() -> _showPasswordErrorSingleLiveEvent.value = R.string.text_msg_please_enter_password
            !checkIfUserExist(_emailStateFlow.value, _passwordStateFlow.value) -> _showPasswordErrorSingleLiveEvent.value = R.string.text_msg_invalid_email_or_password
            else -> _completeLoginSingleLiveEvent.value = true
        }
    }

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _emailStateFlow.value = s.toString()
    }

    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _passwordStateFlow.value = s.toString()
    }

    fun onCreateAccountClick() {
        _onCreateAccountClickSingleLiveEvent.value = true
    }

}

