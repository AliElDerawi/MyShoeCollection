package com.udacity.shoestore.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.udacity.shoestore.data.BaseRecyclerViewAdapter
import me.relex.circleindicator.CircleIndicator3


@BindingAdapter("text")
fun TextView.setContent(text: String) {
    this.text = text
}

@BindingAdapter("number")
fun TextView.setNumber(number: Double) {
    text = number.toString()
}

@BindingAdapter("number")
fun TextView.setNumber(number: Int) {
    text = number.toString()
}

@BindingAdapter("imageSrc")
fun ImageView.setImageSrc(imageId: Int) {
    setImageResource(imageId)
}


@BindingAdapter("recyclerData")
fun <T : Any> RecyclerView.setData(list: MutableList<T>?) {
    if (adapter == null) {
        this.adapter = adapter as? BaseRecyclerViewAdapter<T>
    }
    (adapter as? BaseRecyclerViewAdapter<T>)?.submitList(list)
}

@BindingAdapter("viewPagerData", "circleIndicator" , "currentProgress")
fun <T : Any> ViewPager2.setData(list: MutableList<T>?, circleIndicator: CircleIndicator3, currentProgress: Int?) {
    if (adapter == null) {
        this.adapter = adapter as? BaseRecyclerViewAdapter<T>
    }
    (adapter as? BaseRecyclerViewAdapter<T>)?.submitList(list)
    currentItem = if (currentProgress == -1) 0 else currentProgress?:0
    circleIndicator.setViewPager(this)
}

@BindingAdapter("config","currentProgress")
fun <T : Any> CircularProgressBar.setConfig(list: MutableList<T>?, currentProgress: Int?) {
    list?.let {
        progressMax = it.size.toFloat()
        progress = if (currentProgress == -1) 0f else currentProgress?.plus(1)!!.toFloat()
    }
}

