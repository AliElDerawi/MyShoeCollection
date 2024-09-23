package com.udacity.shoestore.features.createAccount.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.databinding.FragmentCreateAccountBinding
import com.udacity.shoestore.features.createAccount.viewModel.CreateAccountViewModel
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        mBinding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(true)
        mLifecycleOwner = this
        mBinding.lifecycleOwner = this
        mBinding.createAccountViewModel = mViewModel
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

        mViewModel.completeCreateAccountLiveData.observe(mLifecycleOwner) { redirect ->
            if (redirect) {
                getSharedPreference().edit {
                    putBoolean(AppSharedData.PREF_IS_LOGIN, true)
                    putString(
                        AppSharedData.PREF_USER_EMAIL,
                        mBinding.emailTextInputEditText.text.toString()
                    )
                    putString(
                        AppSharedData.PREF_USER_PASSWORD,
                        mBinding.passwordTextInputEditText.text.toString()
                    )
                }
                findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToWelcomeFragment())
            }
        }
    }

}