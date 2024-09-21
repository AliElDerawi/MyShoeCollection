package com.udacity.shoestore.features.onBoarding.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.onBoarding.adapter.OnBoardingAdapter
import com.udacity.shoestore.features.onBoarding.viewModel.InstructionsViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.getInstruction
import com.udacity.shoestore.utils.AppSharedMethods.showToast
import com.udacity.shoestore.models.InstructionModel
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference


class InstructionsFragment : Fragment(), View.OnClickListener {


    private lateinit var mBinding: FragmentInstructionsBinding

    private val mSharedViewModel: MainViewModel by activityViewModels<MainViewModel>()
    private val mInstructionsViewModel: InstructionsViewModel by viewModels<InstructionsViewModel>()


    private lateinit var mActivity: Activity

    private lateinit var mLifecycleOwner: LifecycleOwner


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
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
        mLifecycleOwner = this
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mInstructionsViewModel
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


        mInstructionsViewModel.setLastPage(getTutorialRequestResult.size - 1)
        mBinding.pageCircleProgressBar.progressMax = getTutorialRequestResult.size.toFloat()
        mBinding.pageCircleProgressBar.progress = 1F



        initListeners()
        initBoardingViewPagerListener()
    }


    private fun initListeners() {
        with(mBinding) {
            nextCardView.setOnClickListener(this@InstructionsFragment)
            skipTextView.setOnClickListener(this@InstructionsFragment)
        }
    }

    private fun initViewModelObserver() {

        mInstructionsViewModel.currentPagePageLiveData.observe(mLifecycleOwner) {
            mBinding.pageCircleProgressBar.progress = (it + 1).toFloat()
            mBinding.boardingViewPager.setCurrentItem(mInstructionsViewModel.currentPagePageLiveData.value!!)

            if (mInstructionsViewModel.currentPagePageLiveData.value == mInstructionsViewModel.lastPageLiveData.value) {
                showCompleteButton()
            } else {
                hideCompleteButton()
            }
        }

        mInstructionsViewModel.goNextScreen.observe(mLifecycleOwner) {
            if (it) {
                getSharedPreference().edit {
                    putBoolean(AppSharedData.PREF_IS_NEW_USER, false)
                }

                findNavController().navigate(InstructionsFragmentDirections.actionInstructionsFragmentToShoesListFragment())

            }
        }
    }

    private fun initBoardingViewPagerListener() {

        mBinding.boardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                mInstructionsViewModel.onPageChange(position)

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