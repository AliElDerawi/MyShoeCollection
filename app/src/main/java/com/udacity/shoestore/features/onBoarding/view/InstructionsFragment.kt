package com.udacity.shoestore.features.onBoarding.view

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
import androidx.viewpager2.widget.ViewPager2
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.onBoarding.adapter.OnBoardingAdapter
import com.udacity.shoestore.features.onBoarding.viewModel.InstructionsViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.getInstruction
import com.udacity.shoestore.models.InstructionModel
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class InstructionsFragment : BaseFragment(), View.OnClickListener {


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentInstructionsBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(true)
        mLifecycleOwner = viewLifecycleOwner
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager(mActivity.getInstruction())
        initListeners()
        initViewModelObserver()
    }


    private fun initViewPager(getTutorialRequestResult: List<InstructionModel>) {
        val mOnBoardingPagerAdapter = OnBoardingAdapter(
            getTutorialRequestResult
        )
        mBinding.boardingViewPager.setAdapter(mOnBoardingPagerAdapter)
        mBinding.boardingViewPager.setCurrentItem(0)
        mBinding.boardingScreenCircleIndicator.setViewPager(mBinding.boardingViewPager)


        mViewModel.setLastPage(getTutorialRequestResult.size - 1)
        mBinding.pageCircleProgressBar.progressMax = getTutorialRequestResult.size.toFloat()
        mBinding.pageCircleProgressBar.progress = 1F



        initListeners()
        initBoardingViewPagerListener()
    }


    private fun initListeners() {

    }

    private fun initViewModelObserver() {

        mViewModel.currentPagePageLiveData.observe(mLifecycleOwner) {
            mBinding.pageCircleProgressBar.progress = (it + 1).toFloat()
            mBinding.boardingViewPager.currentItem = (mViewModel.currentPagePageLiveData.value!!)

            if (mViewModel.currentPagePageLiveData.value == mViewModel.lastPageLiveData.value) {
                showCompleteButton()
            } else {
                hideCompleteButton()
            }
        }

        mViewModel.goNextScreen.observe(mLifecycleOwner) {
            if (it) {
                getSharedPreference().edit {
                    putBoolean(AppSharedData.PREF_IS_NEW_USER, false)
                }

                mSharedViewModel.navigationCommand.value =
                    NavigationCommand.To((InstructionsFragmentDirections.actionInstructionsFragmentToShoesListFragment()))
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


    override fun onClick(view: View?) {

    }

}