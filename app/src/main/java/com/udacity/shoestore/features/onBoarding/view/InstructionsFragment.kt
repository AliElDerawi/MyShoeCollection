package com.udacity.shoestore.features.onBoarding.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.onBoarding.adapter.OnBoardingAdapter
import com.udacity.shoestore.features.onBoarding.viewModel.InstructionsViewModel
import com.udacity.shoestore.models.InstructionModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class InstructionsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentInstructionsBinding
    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel: InstructionsViewModel by viewModel()
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
        mBinding = FragmentInstructionsBinding.inflate(inflater, container, false).apply {
            mLifecycleOwner = viewLifecycleOwner
            lifecycleOwner = mLifecycleOwner
            viewModel = mViewModel
        }
        mSharedViewModel.setHideToolbar(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initViewModelObserver()
    }


    private fun initViewPager() {

        mBinding.boardingViewPager.adapter =
            OnBoardingAdapter(InstructionModel.getInstructionCallBack())
        initBoardingViewPagerListener()

    }


    private fun initViewModelObserver() {
        with(mViewModel) {
            currentPageLiveData.observe(mLifecycleOwner) {
                Timber.d("currentPageLiveData: $it")
                if (it == lastPageStateFlow.value) {
                    showCompleteButton()
                } else {
                    hideCompleteButton()
                }
            }

            goNextScreenSingleLiveData.observe(mLifecycleOwner) {
                if (it) {
                    mSharedViewModel.apply {
                        updateNewUserValidation(false)
                        navigationCommand.value =
                            NavigationCommand.To(
                                InstructionsFragmentDirections.actionInstructionsFragmentToShoesListFragment()
                            )
                    }
                }
            }
        }
    }

    private fun initBoardingViewPagerListener() {
        mBinding.boardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mViewModel.onPageChange(position)
            }
        })
    }

    private fun showCompleteButton() {
        mBinding.nextProgressTextView.text = mActivity.getString(R.string.text_start)
    }

    private fun hideCompleteButton() {
        mBinding.nextProgressTextView.text = mActivity.getString(R.string.text_next)
    }

}