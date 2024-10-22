package com.udacity.shoestore.features.placeHolder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.databinding.FragmentPlaceholderBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import org.koin.android.ext.android.inject

class PlaceHolderFragment : BaseFragment() {
    override val mViewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}