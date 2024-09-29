package com.udacity.shoestore.features.welcome.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentWelcomeBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.welcome.viewModel.WelcomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeFragment : BaseFragment() {


    private lateinit var mBinding: FragmentWelcomeBinding

    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel: WelcomeViewModel by viewModel()

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
        mBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(true)
        mBinding.welcomeFragment = this
        mBinding.lifecycleOwner = this
        mLifecycleOwner = viewLifecycleOwner
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

    }

    fun onNextCardClick() {
        mSharedViewModel.navigationCommand.value = NavigationCommand.To(WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment2())
    }


}