package com.udacity.shoestore.features.shoeDetail.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.TextWatcherAdapter
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedMethods.isEmpty
import com.udacity.shoestore.utils.AppSharedMethods.showToast
import java.util.Objects


class ShoeDetailFragment : Fragment() {

    private lateinit var mBinding: FragmentShoeDetailBinding

    private val mSharedViewModel: MainViewModel by activityViewModels<MainViewModel>()
    private val mShoesDetailViewModel : ShoeDetailViewModel by viewModels<ShoeDetailViewModel>()

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(false)
        mSharedViewModel.showUpButton(true)
        mLifecycleOwner = this
        mBinding.lifecycleOwner = this
        mBinding.shoeDetailViewModel = mShoesDetailViewModel
        initViewModelObserver()
//        (mActivity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initViewModelObserver(){
        mShoesDetailViewModel.onProcessSaveShoe.observe(mLifecycleOwner){
            if(it != null){
                mSharedViewModel.addShoe(it)
                findNavController().popBackStack()
            }
        }

        mShoesDetailViewModel.onCancelClick.observe(mLifecycleOwner){
            if(it){
                findNavController().popBackStack()
            }
        }
    }

}