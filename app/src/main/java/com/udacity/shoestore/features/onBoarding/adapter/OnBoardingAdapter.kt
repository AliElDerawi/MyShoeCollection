package com.udacity.shoestore.features.onBoarding.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.databinding.ItemOnboardingPageBinding
import com.udacity.shoestore.models.InstructionModel
import com.udacity.shoestore.utils.AppSharedMethods.setImageInAdapter

class OnBoardingAdapter(val instructionModelList: List<InstructionModel>) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOnboardingPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        if (instructionModelList != null) {
            return instructionModelList.size
        } else {
            return 0;
        }
    }

    inner class ViewHolder(val mBinding: ItemOnboardingPageBinding) :
        RecyclerView.ViewHolder(mBinding.root) {


        fun bind(position: Int) {

            val instructionModel = instructionModelList[position]
            val context = mBinding.iconImageView.context

            mBinding.iconImageView.setImageResource(instructionModel.drawable)

//            context.setImageInAdapter(
//                instructionModel.drawable,
//                mBinding.iconImageView
//            )

            mBinding.titleTextView.text = instructionModel.title
            mBinding.contentTextView.text = instructionModel.description
        }

    }
}