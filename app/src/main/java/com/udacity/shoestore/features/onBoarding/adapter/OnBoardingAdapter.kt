package com.udacity.shoestore.features.onBoarding.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseRecyclerViewAdapter
import com.udacity.shoestore.databinding.ItemOnboardingPageBinding
import com.udacity.shoestore.models.InstructionModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedMethods.setImageInAdapter

class OnBoardingAdapter(
    diffCallback: DiffUtil.ItemCallback<InstructionModel>,
    callback: ((item: InstructionModel) -> Unit)? = null
) : BaseRecyclerViewAdapter<InstructionModel>(diffCallback, callback) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_onboarding_page
}