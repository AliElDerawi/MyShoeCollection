package com.udacity.shoestore.utils

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.udacity.shoestore.features.createAccount.viewModel.CreateAccountViewModel
import com.udacity.shoestore.features.login.viewModel.LoginViewModel
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.onBoarding.viewModel.InstructionsViewModel
import com.udacity.shoestore.features.shoeDetail.view.ShoeDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ShoeStoreApp : MultiDexApplication() {


    companion object {
        @Volatile
        var mShoeStoreAppInstance: ShoeStoreApp? = null

        fun getInstance(): ShoeStoreApp? {
            if (mShoeStoreAppInstance == null) {
                synchronized(ShoeStoreApp::class.java) {
                    if (mShoeStoreAppInstance == null) mShoeStoreAppInstance = ShoeStoreApp()
                }
            }
            return mShoeStoreAppInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        mShoeStoreAppInstance = this

        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()

            viewModel {
                LoginViewModel(get())
            }

            viewModel {
                CreateAccountViewModel(get())
            }

            viewModel {
                InstructionsViewModel(get())
            }

            viewModel {
                ShoeDetailViewModel(get())
            }

            single {
                MainViewModel()
            }

        }

        startKoin {
            androidContext(this@ShoeStoreApp)
            modules(listOf(myModule))
        }
    }


}