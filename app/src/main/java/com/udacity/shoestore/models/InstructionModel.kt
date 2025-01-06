package com.udacity.shoestore.models

import com.udacity.shoestore.data.GenericModelCallBack

data class InstructionModel(
    val title: String,
    val description: String,
    val drawable: Int
) {

    companion object{
        fun getInstructionCallBack(): GenericModelCallBack<InstructionModel> {
            return GenericModelCallBack(_areItemsTheSame = { oldItem, newItem ->
                oldItem.title == newItem.title && oldItem.description == newItem.description
            }, _areContentsTheSame = { oldItem, newItem -> oldItem == newItem })
        }
    }
}