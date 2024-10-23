package com.udacity.shoestore.features.login.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.features.login.viewModel.LoginViewModel
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {


    private lateinit var mBinding: FragmentLoginBinding

    private val mSharedViewModel: MainViewModel by inject()

    override val mViewModel: LoginViewModel by viewModel()

    private lateinit var mActivity: FragmentActivity

    private lateinit var mLifecycleOwner: LifecycleOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(true)
        mBinding.loginViewModel = mViewModel
        mLifecycleOwner = viewLifecycleOwner
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViewModelObserver()
    }

    private fun initListeners() {

    }

    private fun initViewModelObserver() {

        with(mBinding) {
            mViewModel.completeLoginLiveData.observe(mLifecycleOwner) { redirect ->
                if (redirect) {
                    getSharedPreference().edit {
                        putBoolean(AppSharedData.PREF_IS_LOGIN, true)
                    }
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        if (mSharedViewModel.isNewUserFlow.first()) {
                            mSharedViewModel.navigationCommand.postValue(
                                NavigationCommand.To(
                                    LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
                                )
                            )
                        } else {
                            mSharedViewModel.navigationCommand.postValue(
                                NavigationCommand.To(LoginFragmentDirections.actionLoginFragmentToShoesListFragment())
                            )
                        }
                    }

                }
            }

            mViewModel.onCreateAccountClick.observe(mLifecycleOwner) {
                if (it) {
                    mSharedViewModel.navigationCommand.value =
                        NavigationCommand.To(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
                }
            }

            mViewModel.showEmailError.observe(mLifecycleOwner) {
                emailTextInputEditText.error = mActivity.getString(it)
                mViewModel.showToastInt.value = it
            }

            mViewModel.showPasswordError.observe(mLifecycleOwner) {
                mViewModel.showToastInt.value = it
            }
        }
    }

}