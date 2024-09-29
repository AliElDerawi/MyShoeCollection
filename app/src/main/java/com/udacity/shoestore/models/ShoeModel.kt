package com.udacity.shoestore.models

import android.os.Parcelable
import com.udacity.shoestore.data.GenericModelCallBack
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoeModel(
    var name: String,
    var size: Double,
    var company: String,
    var description: String,
    val images: List<String> = mutableListOf()
) : Parcelable {


    companion object{
        fun getShoeModelCallback(): GenericModelCallBack<ShoeModel> {
            return GenericModelCallBack(_areItemsTheSame = { oldItem, newItem ->
                oldItem.name == newItem.name && oldItem.company == newItem.company && oldItem.size == newItem.size
            }, _areContentsTheSame = { oldItem, newItem -> oldItem == newItem })
        }
    }
}
