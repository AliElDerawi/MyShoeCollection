package com.udacity.shoestore.features.shoeDetail.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.shoeDetail.viewModel.ShoeDetailViewModel
import com.udacity.shoestore.utils.AppSharedMethods.setButtonStyle
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShoeDetailFragment : BaseFragment() {

    private lateinit var mBinding: FragmentShoeDetailBinding
    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel: ShoeDetailViewModel by viewModel()
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
        mBinding = FragmentShoeDetailBinding.inflate(inflater, container, false).apply {
            mLifecycleOwner = viewLifecycleOwner
            lifecycleOwner = mLifecycleOwner
            shoeDetailViewModel = mViewModel
        }
        mSharedViewModel.apply {
            setHideToolbar(false)
            showUpButton(true)
        }
        initViewModelObserver()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModelObserver() {
        with(mViewModel) {
            onProcessSaveShoeSingleLiveEvent.observe(mLifecycleOwner) {
                it?.let {
                    mSharedViewModel.addShoe(it)
                    mSharedViewModel.navigationCommand.value = NavigationCommand.Back
                }
            }

            onCancelClickSingleLiveEvent.observe(mLifecycleOwner) {
                if (it) {
                    mSharedViewModel.navigationCommand.value = NavigationCommand.Back
                }
            }

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    isSaveButtonEnabledStateFlow.collect { isEnabled ->
                        mBinding.saveButton.setButtonStyle(isEnabled)
                    }
                }
            }
        }
    }

}