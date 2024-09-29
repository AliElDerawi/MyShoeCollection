package com.udacity.shoestore.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.udacity.shoestore.R
import com.udacity.shoestore.models.InstructionModel

object AppSharedMethods {

    private var mToast: Toast? = null

    fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(ShoeStoreApp.getApp().applicationContext, message, duration)
        mToast!!.show()
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(
            ShoeStoreApp.getApp().applicationContext, message, duration
        )
        mToast!!.show()
    }

    fun showToast(message: Int, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(
            ShoeStoreApp.getApp().applicationContext, message, duration
        )
        mToast!!.show()
    }


    fun EditText.isEmpty(): Boolean {
        return this.text.toString().isEmpty()
    }

    fun EditText.isValidEmail(): Boolean {
        return !this.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()
    }

    fun String.isValidEmail(): Boolean {
        return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun Application.getInstruction(): List<InstructionModel> {
        val instructions = mutableListOf<InstructionModel>()
        instructions.add(
            InstructionModel(
                getString(R.string.text_instruction_title_01),
                getString(R.string.text_instruction_content_01),
                R.drawable.ic_shoe
            )
        )
        instructions.add(
            InstructionModel(
                getString(R.string.text_instruction_title_02),
                getString(R.string.text_instruction_content_02),
                R.drawable.ic_shoe
            )
        )
        instructions.add(
            InstructionModel(
                getString(R.string.text_instruction_03),
                getString(R.string.text_instruction_content_03),
                R.drawable.ic_shoe
            )
        )
        return instructions
    }

    fun Context.setImageInAdapter(
        drawable: Int, mTargetImageView: ImageView
    ) {
        Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, drawable, null))
            .into(mTargetImageView)
    }

    fun getSharedPreference(): SharedPreferences {
        return ShoeStoreApp.getApp()
            .getSharedPreferences(AppSharedData.MY_PREF, Context.MODE_PRIVATE)
    }

    fun checkIfUserExist(email: String, password: String): Boolean {
        val sharedPreferences = getSharedPreference()
        val storedEmail = sharedPreferences.getString(AppSharedData.PREF_USER_EMAIL, null)
        val storedPassword = sharedPreferences.getString(AppSharedData.PREF_USER_PASSWORD, null)

        return storedEmail == email && storedPassword == password
    }

    fun Activity.getCompatColor(color: Int): Int {
        return ResourcesCompat.getColor(resources, color, null)
    }

    fun Activity.getCompatColorStateList(color: Int): ColorStateList {
        return ColorStateList.valueOf(
            ResourcesCompat.getColor(
                resources, color, null
            )
        )
    }
}