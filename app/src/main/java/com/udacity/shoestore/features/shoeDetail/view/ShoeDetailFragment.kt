package com.udacity.shoestore.features.shoeDetail.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShoeDetailFragment : BaseFragment() {

    private lateinit var mBinding: FragmentShoeDetailBinding

    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel : ShoeDetailViewModel by viewModel()

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(false)
        mSharedViewModel.showUpButton(true)
        mLifecycleOwner = this
        mBinding.lifecycleOwner = this
        mBinding.shoeDetailViewModel = mViewModel
        initViewModelObserver()
//        (mActivity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initViewModelObserver(){
        mViewModel.onProcessSaveShoe.observe(mLifecycleOwner){
            if(it != null){
                mSharedViewModel.addShoe(it)
                mSharedViewModel.navigationCommand.value = NavigationCommand.Back
            }
        }

        mViewModel.onCancelClick.observe(mLifecycleOwner){
            if(it){
                mSharedViewModel.navigationCommand.value = NavigationCommand.Back
            }
        }
    }

}