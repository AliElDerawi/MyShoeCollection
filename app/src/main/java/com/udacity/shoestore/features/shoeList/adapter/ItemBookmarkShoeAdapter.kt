package com.udacity.shoestore.features.shoeList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseRecyclerViewAdapter
import com.udacity.shoestore.models.ShoeModel

class ItemBookmarkShoeAdapter(
    diffCallback: DiffUtil.ItemCallback<ShoeModel>, callback: ((item: ShoeModel) -> Unit)? = null
) : BaseRecyclerViewAdapter<ShoeModel>(diffCallback, callback) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_bookmarked_shoe
}