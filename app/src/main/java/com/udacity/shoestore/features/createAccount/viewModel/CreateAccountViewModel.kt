package com.udacity.shoestore.features.createAccount.viewModel

import android.app.Activity
import android.app.Application
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseViewModel
import com.udacity.shoestore.utils.AppSharedMethods.isEmpty
import com.udacity.shoestore.utils.AppSharedMethods.isValidEmail
import com.udacity.shoestore.utils.AppSharedMethods.showToast
import com.udacity.shoestore.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateAccountViewModel(val app: Application) : BaseViewModel(app)  {

    private val _completeCreateAccountLiveData = SingleLiveEvent<Boolean>()
    val completeCreateAccountLiveData: MutableLiveData<Boolean>
        get() = _completeCreateAccountLiveData

    private val _emailLiveData = MutableStateFlow("")
    val emailLiveData: StateFlow<String>
        get() = _emailLiveData

    private val _passwordLiveData = MutableStateFlow("")
    val passwordLiveData: StateFlow<String>
        get() = _passwordLiveData

    private val _confirmPasswordLiveData = MutableStateFlow("")
    val confirmPasswordLiveData: StateFlow<String>
        get() = _confirmPasswordLiveData

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _emailLiveData.value = s.toString()
    }

    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _passwordLiveData.value = s.toString()
    }

    fun onConfirmPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _confirmPasswordLiveData.value = s.toString()
    }


    fun createAccount(
    ) {
        if (_emailLiveData.value.isEmpty()) {
            showToastInt.value = R.string.text_msg_please_enter_email
        } else if (!_emailLiveData.value.isValidEmail()) {
            showToastInt.value = R.string.text_msg_enter_valid_email_address
        } else if (_passwordLiveData.value.isEmpty()) {
            showToastInt.value = R.string.text_msg_please_enter_password
        } else if (_confirmPasswordLiveData.value.isEmpty()) {
            showToastInt.value = R.string.text_msg_please_enter_confirm_password
        } else if (_passwordLiveData.value != _confirmPasswordLiveData.value) {
            showToastInt.value = R.string.text_msg_password_mismatch
        } else {
            _completeCreateAccountLiveData.value = true
        }
    }
}

