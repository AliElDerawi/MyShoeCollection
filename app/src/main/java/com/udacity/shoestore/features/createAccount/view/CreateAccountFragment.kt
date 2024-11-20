package com.udacity.shoestore.features.createAccount.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentCreateAccountBinding
import com.udacity.shoestore.features.createAccount.viewModel.CreateAccountViewModel
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.createAccountAndLogin
import com.udacity.shoestore.utils.AppSharedMethods.getCompatColor
import com.udacity.shoestore.utils.AppSharedMethods.getCompatColorStateList
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
import com.udacity.shoestore.utils.AppSharedMethods.setButtonStyle
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateAccountFragment : BaseFragment() {

    private lateinit var mBinding: FragmentCreateAccountBinding
    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel: CreateAccountViewModel by viewModel()
    private lateinit var mActivity: FragmentActivity
    private lateinit var mLifecycleOwner: LifecycleOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentCreateAccountBinding.inflate(inflater, container, false)
            .apply {
                mLifecycleOwner = viewLifecycleOwner
                lifecycleOwner = mLifecycleOwner
                createAccountViewModel = mViewModel
            }
        mSharedViewModel.setHideToolbar(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObserver()
    }

    private fun initViewModelObserver() {
        with(mViewModel) {
            completeCreateAccountLiveData.observe(mLifecycleOwner) { redirect ->
                if (redirect) {
                    createAccountAndLogin(
                        mBinding.emailTextInputEditText.text.toString(),
                        mBinding.passwordTextInputEditText.text.toString()
                    )
                    mSharedViewModel.navigationCommand.value = NavigationCommand.To(
                        CreateAccountFragmentDirections.actionCreateAccountFragmentToWelcomeFragment()
                    )
                }
            }

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    isCreateAccountButtonEnabledStateFlow.collect { isEnabled ->
                        mBinding.createAccountButton.setButtonStyle(isEnabled)
                    }
                }
            }
        }
    }

}