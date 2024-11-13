package com.udacity.shoestore.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object AppSharedData {

    const val MY_ENCRYPTED_PREF = "MY_ENCRYPTED_PREF"
    const val PREF_USER_EMAIL = "USER_EMAIL"
    const val PREF_USER_PASSWORD = "USER_PASSWORD"
    const val PREF_IS_LOGIN = "IS_LOGIN"

    const val MY_USER_PREFERENCES = "USER_PREFERENCES"
    val PREF_IS_NEW_USER = booleanPreferencesKey("IS_NEW_USER")

}