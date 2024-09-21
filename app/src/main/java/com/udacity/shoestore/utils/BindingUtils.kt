package com.udacity.shoestore.utils

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.shoestore.models.ShoeModel

@BindingAdapter("shoeName")
fun TextView.setShoeName(item: ShoeModel) {
    text = item.name
}

@BindingAdapter("shoeCompany")
fun TextView.setShoeCompany(item: ShoeModel) {
    text = item.company
}

@BindingAdapter("shoeSize")
fun TextView.setShoeSize(item: ShoeModel) {
    text = item.size.toString()
}

@BindingAdapter("shoeDescription")
fun TextView.setShoeDescription(item: ShoeModel) {
    text = item.description
}

@BindingAdapter("editShoeName")
fun EditText.saveShoeName(text : String){


}