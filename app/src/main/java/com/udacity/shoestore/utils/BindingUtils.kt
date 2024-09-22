package com.udacity.shoestore.utils

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.shoestore.models.ShoeModel



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
