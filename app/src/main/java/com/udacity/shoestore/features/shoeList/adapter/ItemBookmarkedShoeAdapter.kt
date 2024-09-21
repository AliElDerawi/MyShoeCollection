package com.udacity.shoestore.features.shoeList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.databinding.ItemBookmarkedShoeBinding
import com.udacity.shoestore.models.ShoeModel

class ItemBookmarkedShoeAdapter() :
    ListAdapter<ShoeModel, ItemBookmarkedShoeAdapter.ViewHolder>(ShoeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(val mBinding: ItemBookmarkedShoeBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: ShoeModel) {
            mBinding.shoe = item
            mBinding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookmarkedShoeBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)

            }
        }

    }
}

class ShoeDiffCallback : DiffUtil.ItemCallback<ShoeModel>() {
    override fun areItemsTheSame(oldItem: ShoeModel, newItem: ShoeModel): Boolean {
        return oldItem.name == newItem.name && oldItem.company == newItem.company && oldItem.size == newItem.size
    }

    override fun areContentsTheSame(oldItem: ShoeModel, newItem: ShoeModel): Boolean {
        return oldItem == newItem
    }
}
