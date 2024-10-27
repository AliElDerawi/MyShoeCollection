package com.udacity.shoestore.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
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
        mToast = Toast.makeText(ShoeStoreApp.getApp().applicationContext, message, duration)
        mToast!!.show()
    }

    fun showToast(message: Int, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(ShoeStoreApp.getApp().applicationContext, message, duration)
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
        return listOf(
            InstructionModel(
                getString(R.string.text_instruction_title_01),
                getString(R.string.text_instruction_content_01),
                R.drawable.ic_shoe
            ), InstructionModel(
                getString(R.string.text_instruction_title_02),
                getString(R.string.text_instruction_content_02),
                R.drawable.ic_shoe
            ), InstructionModel(
                getString(R.string.text_instruction_03),
                getString(R.string.text_instruction_content_03),
                R.drawable.ic_shoe
            )
        )
    }

    fun Context.setImageInAdapter(
        drawable: Int, mTargetImageView: ImageView
    ) {
        Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, drawable, null))
            .into(mTargetImageView)
    }

    fun getSharedPreference(): SharedPreferences {
        return getEncryptedSharedPrefs(ShoeStoreApp.getApp())
    }

    fun checkIfUserExist(email: String, password: String): Boolean {
        val sharedPreferences = getEncryptedSharedPrefs(ShoeStoreApp.getApp())
        val storedEmail = sharedPreferences.getString(AppSharedData.PREF_USER_EMAIL, null)
        val storedPassword = sharedPreferences.getString(AppSharedData.PREF_USER_PASSWORD, null)

        return storedEmail == email && storedPassword == password
    }

    fun Context.getCompatColor(color: Int): Int {
        return ResourcesCompat.getColor(resources, color, null)
    }

    fun Context.getCompatColorStateList(color: Int): ColorStateList {
        return ColorStateList.valueOf(
            ResourcesCompat.getColor(
                resources, color, null
            )
        )
    }

    fun isLogin(): Boolean {
        return getSharedPreference().getBoolean(AppSharedData.PREF_IS_LOGIN, false)
    }

    private fun getEncryptedSharedPrefs(context: Context): SharedPreferences {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            context,
            AppSharedData.MY_ENCRYPTED_PREF,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun MaterialButton.setButtonStyle(isEnabled: Boolean) {
        val backgroundColorStateList = if (isEnabled) {
            context.getCompatColorStateList(R.color.colorAccent)
        } else {
            context.getCompatColorStateList(R.color.colorGrayF2)
        }
        val strokeColorStateList = if (isEnabled) {
            context.getCompatColorStateList(R.color.colorAccent)
        } else {
            context.getCompatColorStateList(R.color.colorGray63)
        }
        val textColorCompat = if (isEnabled) {
            context.getCompatColor(R.color.colorWhite)
        } else {
            context.getCompatColor(R.color.colorBlack)
        }
        apply {
            backgroundTintList = backgroundColorStateList
            strokeColor = strokeColorStateList
            setTextColor(textColorCompat)
        }
    }

    fun setLoginStatus(isLogin: Boolean) {
        getSharedPreference().edit {
            putBoolean(AppSharedData.PREF_IS_LOGIN, isLogin)
        }
    }

    fun createAccountAndLogin(email: String, password: String) {
        getSharedPreference().edit {
            putString(AppSharedData.PREF_USER_EMAIL, email)
            putString(AppSharedData.PREF_USER_PASSWORD, password)
            putBoolean(AppSharedData.PREF_IS_LOGIN, true)
        }
    }
}