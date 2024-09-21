package com.udacity.shoestore.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.udacity.shoestore.R
import com.udacity.shoestore.models.InstructionModel

object AppSharedMethods {

    private var mToast: Toast? = null

    fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(ShoeStoreApp.getInstance()!!.applicationContext, message, duration)
        mToast!!.show()
    }

    fun showToast(message: Int, duration: Int = Toast.LENGTH_LONG) {
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(
            ShoeStoreApp.getInstance()!!.applicationContext,
            ShoeStoreApp.getInstance()!!.applicationContext.getString(message),
            duration
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
        return !this.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()
    }

    fun Activity.getInstruction(): List<InstructionModel> {
        val instructions = mutableListOf<InstructionModel>()
//        instructions.add(InstructionModel("Welcome to ShoeStore", "In the ShoeStore app. You can add shoes to your bookmark and view them.", R.drawable.ic_shoe))
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
        GlideApp.with(this).load(ResourcesCompat.getDrawable(this.resources, drawable, null))
            .into(mTargetImageView)
    }

    fun getSharedPreference(): SharedPreferences {
        return ShoeStoreApp.getInstance()!!
            .getSharedPreferences(AppSharedData.MY_PREF, Context.MODE_PRIVATE)
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun checkIfUserExist(email: String, password: String): Boolean {
        if (getSharedPreference().contains(AppSharedData.PREF_USER_EMAIL)) {
            return getSharedPreference().getString(AppSharedData.PREF_USER_EMAIL, "") == email
                    && getSharedPreference().getString(
                AppSharedData.PREF_USER_PASSWORD,
                ""
            ) == password
        } else {
            return false
        }

    }
}