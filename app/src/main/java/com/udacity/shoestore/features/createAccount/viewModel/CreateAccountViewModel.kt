package com.udacity.shoestore.features.createAccount.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.utils.AppSharedMethods.isValidEmail
import com.udacity.shoestore.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CreateAccountViewModel(val app: Application) : BaseViewModel(app) {

    private var _completeCreateAccountSingleLiveEvent = SingleLiveEvent<Boolean>()
    val completeCreateAccountLiveData: LiveData<Boolean>
        get() = _completeCreateAccountSingleLiveEvent

    private var _emailStateStateFlow = MutableStateFlow("")
    val emailStateFlow: StateFlow<String>
        get() = _emailStateStateFlow

    private var _passwordStateFlow = MutableStateFlow("")
    val passwordStateFlow: StateFlow<String>
        get() = _passwordStateFlow

    private var _confirmPasswordStateFlow = MutableStateFlow("")
    val confirmPasswordStateFlow: StateFlow<String>
        get() = _confirmPasswordStateFlow

    val isCreateAccountButtonEnabledStateFlow: StateFlow<Boolean> = combine(
        _emailStateStateFlow, _passwordStateFlow, _confirmPasswordStateFlow
    ) { email, password, confirmPassword ->
        email.isNotEmpty() && email.isValidEmail() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _emailStateStateFlow.value = s.toString()
    }

    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _passwordStateFlow.value = s.toString()
    }

    fun onConfirmPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _confirmPasswordStateFlow.value = s.toString()
    }

    fun createAccount(
    ) {
        when {
            _emailStateStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_email
            !_emailStateStateFlow.value.isValidEmail() -> showToastInt.value = R.string.text_msg_enter_valid_email_address
            _passwordStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_password
            _confirmPasswordStateFlow.value.isEmpty() -> showToastInt.value = R.string.text_msg_please_enter_confirm_password
            _passwordStateFlow.value != _confirmPasswordStateFlow.value -> showToastInt.value = R.string.text_msg_password_mismatch
            else -> _completeCreateAccountSingleLiveEvent.value = true
        }
    }
}

