package com.udacity.shoestore.features.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mMainViewModel: MainViewModel by inject()

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.mainToolbar)
        mBinding.mainToolbar.setTitle(null)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        initViewModelObservers()
        initListeners()

    }

    private fun initViewModelObservers() {
        mMainViewModel.hideToolbar.observe(this) {
            if (it) {
                mBinding.mainToolbar.visibility = View.GONE
            } else {
                mBinding.mainToolbar.visibility = View.VISIBLE
            }
        }

        mMainViewModel.toolbarTitle.observe(this) {
            mBinding.textViewToolbarTitle.text = it
        }

        mMainViewModel.showUpButton.observe(this){
            supportActionBar!!.setDisplayHomeAsUpEnabled(it)
        }

        mMainViewModel.navigationCommand.observe(this) { command ->

            Timber.d("initViewModelObserver:command: " + command.toString())

            when (command) {
                is NavigationCommand.To -> navController.navigate(command.directions)
                is NavigationCommand.Back -> onBackPressed()
                is NavigationCommand.BackTo -> navController.popBackStack(
                    command.destinationId,
                    false
                )
            }
        }
    }

    private fun initListeners() {


        if (getSharedPreference().getBoolean(AppSharedData.PREF_IS_LOGIN, true)) {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onClick(view: View?) {

    }
}
