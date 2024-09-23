package com.udacity.shoestore.features.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.utils.AppSharedMethods.checkIfUserExist
import com.udacity.shoestore.utils.AppSharedMethods.isValidEmail
import com.udacity.shoestore.utils.AppSharedMethods.showToast
import com.udacity.shoestore.utils.SingleLiveEvent

class LoginViewModel : ViewModel() {

    private val _completeLoginLiveData = SingleLiveEvent<Boolean>()
    val completeLoginLiveData: LiveData<Boolean>
        get() = _completeLoginLiveData

    private val _emailLiveDate = MutableLiveData<String>("")
    val emailLiveData: MutableLiveData<String>
        get() = _emailLiveDate

    private val _passwordLiveData = MutableLiveData<String>("")
    val passwordLiveData: MutableLiveData<String>
        get() = _passwordLiveData

    private val _onCreateAccountClick = SingleLiveEvent<Boolean>()
    val onCreateAccountClick: LiveData<Boolean>
        get() = _onCreateAccountClick


    fun login() {
        if (_emailLiveDate.value!!.isEmpty()) {
            showToast(R.string.text_msg_please_enter_email)
        } else if (!_emailLiveDate.value!!.isValidEmail()) {
            showToast(R.string.text_msg_enter_valid_email_address)
        } else if (_passwordLiveData.value!!.isEmpty()) {
            showToast(R.string.text_msg_please_enter_password)
        } else if (!checkIfUserExist(_emailLiveDate.value!!, _passwordLiveData.value!!)) {
            showToast(R.string.text_msg_invalid_email_or_password)
        } else {
            _completeLoginLiveData.value = true
        }
    }

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _emailLiveDate.value = s.toString()
    }

    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _passwordLiveData.value = s.toString()
    }

    fun onCreateAccountClick() {
        _onCreateAccountClick.value = true
    }

}

