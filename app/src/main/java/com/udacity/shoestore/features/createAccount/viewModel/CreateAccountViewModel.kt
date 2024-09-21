package com.udacity.shoestore.features.createAccount.viewModel

import android.app.Activity
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.utils.AppSharedMethods.isEmpty
import com.udacity.shoestore.utils.AppSharedMethods.isValidEmail
import com.udacity.shoestore.utils.AppSharedMethods.showToast

class CreateAccountViewModel : ViewModel() {

    private val _completeCreateAccountLiveData = MutableLiveData<Boolean>()

    val completeCreateAccountLiveData: MutableLiveData<Boolean>
        get() = _completeCreateAccountLiveData

    private val _emailLiveData = MutableLiveData<String>("")
    val emailLiveData: MutableLiveData<String>
        get() = _emailLiveData

    private val _passwordLiveData = MutableLiveData<String>("")
    val passwordLiveData: MutableLiveData<String>
        get() = _passwordLiveData

    private val _confirmPasswordLiveData = MutableLiveData<String>("")
    val confirmPasswordLiveData: MutableLiveData<String>
        get() = _confirmPasswordLiveData

    fun setCompleteCreateAccount(completeCreateAccount: Boolean) {
        _completeCreateAccountLiveData.value = completeCreateAccount
    }


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
        if (_emailLiveData.value!!.isEmpty()) {
            showToast(R.string.text_msg_please_enter_email)
        } else if (!_emailLiveData.value!!.isValidEmail()) {
            showToast(R.string.text_msg_enter_valid_email_address)
        } else if (_passwordLiveData.value!!.isEmpty()) {
            showToast(R.string.text_msg_please_enter_password)
        } else if (_confirmPasswordLiveData.value!!.isEmpty()) {
            showToast(R.string.text_msg_please_enter_confirm_password)
        } else if (_passwordLiveData.value.toString() != _confirmPasswordLiveData.value.toString()) {
            showToast(R.string.text_msg_password_mismatch)
        } else {
            _completeCreateAccountLiveData.value = true
        }
    }
}

