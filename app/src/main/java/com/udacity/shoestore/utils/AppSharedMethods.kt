package com.udacity.shoestore.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.udacity.shoestore.R
import com.udacity.shoestore.models.InstructionModel

object AppSharedMethods {

    private var mToast: Toast? = null

    fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(ShoeStoreApp.getApp().applicationContext, message, duration)
        mToast!!.show()
    }

    fun Activity.showToast(message: Int, duration: Int = Toast.LENGTH_LONG) {
        mToast?.cancel()
        mToast = Toast.makeText(ShoeStoreApp.getApp().applicationContext, getString(message), duration)
        mToast!!.show()
    }

    fun EditText.isEmpty(): Boolean {
        return this.text.toString().isEmpty()
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

    fun View.applyWindowsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun Activity.setStatusBarColorAndStyle(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Set the listener to customize the status bar area
            window.decorView.apply {
                setOnApplyWindowInsetsListener { v, insets ->
                    val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                    // Create a view for the status bar background
                    val statusBarBackground = View(this@setStatusBarColorAndStyle).apply {
                        setBackgroundColor(color)
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            statusBarInsets.top // Height matches the status bar inset
                        )
                    }
                    // Add the view to the decorView
                    (this as ViewGroup).apply {
                        if (statusBarBackground.parent == null) {
                            addView(statusBarBackground)
                        }
                    }
                    insets
                }
                // Request insets to trigger the listener
                requestApplyInsets()
            }

            if (color == Color.BLACK || ColorUtils.calculateLuminance(color) < 0.5) {
                // If the color is dark, use light icons
                window.insetsController?.apply {
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            } else {
                // If the color is light, use dark icons
                window.insetsController?.apply {
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            }

        } else {
            // Fallback for older versions
            window.apply {
                statusBarColor = color
                navigationBarColor = color
                decorView.systemUiVisibility = when {
                    ColorUtils.calculateLuminance(statusBarColor) >= 0.5 -> {
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    ColorUtils.calculateLuminance(navigationBarColor) >= 0.5 -> {
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    else -> 0 // No flags for dark icons
                }
            }
        }
    }

    fun Toolbar.setMenuColor(color: Int) {
        overflowIcon?.setTint(context.getCompatColor(color))
    }

    fun NavController.validateStartDestination(){
        val navGraph = navInflater.inflate(R.navigation.main_navigation).apply {
            setStartDestination(
                if (isLogin()) {
                    R.id.shoesListFragment
                } else {
                    R.id.loginFragment
                }
            )
        }
        graph = navGraph
    }

}