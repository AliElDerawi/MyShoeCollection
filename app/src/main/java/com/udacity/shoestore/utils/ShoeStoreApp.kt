package com.udacity.shoestore.utils

import androidx.multidex.MultiDexApplication

class ShoeStoreApp : MultiDexApplication() {


    companion object {
        @Volatile
        var mShoeStoreAppInstance: ShoeStoreApp? = null

        fun getInstance(): ShoeStoreApp? {
            if (mShoeStoreAppInstance == null) {
                synchronized(ShoeStoreApp::class.java) {
                    if (mShoeStoreAppInstance == null)
                        mShoeStoreAppInstance = ShoeStoreApp()
                }
            }
            return mShoeStoreAppInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        mShoeStoreAppInstance = this
    }


}