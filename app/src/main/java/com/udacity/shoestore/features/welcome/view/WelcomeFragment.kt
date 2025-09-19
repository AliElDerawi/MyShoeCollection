package com.udacity.shoestore.features.welcome.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentWelcomeBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.welcome.viewModel.WelcomeViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentWelcomeBinding
    private val mSharedViewModel: MainViewModel by activityViewModel()
    override val mViewModel: WelcomeViewModel by viewModel()
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
        mBinding = FragmentWelcomeBinding.inflate(inflater, container, false).apply {
            welcomeFragment = this@WelcomeFragment
            mLifecycleOwner = viewLifecycleOwner
            lifecycleOwner = mLifecycleOwner
        }
        mSharedViewModel.setHideToolbar(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onNextCardClick() {
        mSharedViewModel.navigationCommand.value = NavigationCommand.To(
            WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment2()
        )
    }

}