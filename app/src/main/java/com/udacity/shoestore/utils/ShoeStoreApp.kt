package com.udacity.shoestore.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.multidex.MultiDexApplication
import com.udacity.shoestore.features.createAccount.viewModel.CreateAccountViewModel
import com.udacity.shoestore.features.login.viewModel.LoginViewModel
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.onBoarding.viewModel.InstructionsViewModel
import com.udacity.shoestore.features.shoeDetail.viewModel.ShoeDetailViewModel
import com.udacity.shoestore.features.shoeList.viewModel.ShoeListViewModel
import com.udacity.shoestore.features.welcome.viewModel.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import timber.log.Timber

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppSharedData.MY_USER_PREFERENCES)

class ShoeStoreApp : MultiDexApplication() {

    companion object {
        @Volatile
        var mShoeStoreApp: ShoeStoreApp? = null

        fun getApp(): ShoeStoreApp {
            return mShoeStoreApp ?: synchronized(this) {
                mShoeStoreApp ?: ShoeStoreApp().also { mShoeStoreApp = it }
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        mShoeStoreApp = this
        Timber.plant(Timber.DebugTree())

        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModelOf(::LoginViewModel)
            viewModelOf(::CreateAccountViewModel)
            viewModelOf(::InstructionsViewModel)
            viewModelOf(::ShoeDetailViewModel)
            viewModelOf(::WelcomeViewModel)
            viewModelOf(::ShoeListViewModel)
            singleOf(::MainViewModel)
        }

        startKoin {
            androidContext(this@ShoeStoreApp)
            modules(listOf(myModule))
        }
    }


}