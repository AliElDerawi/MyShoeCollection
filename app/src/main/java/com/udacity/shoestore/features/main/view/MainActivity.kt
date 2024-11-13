package com.udacity.shoestore.features.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.utils.AppSharedMethods
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mMainViewModel: MainViewModel by inject()
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            setSupportActionBar(mainToolbar)
            mainToolbar.setTitle(null)
        }
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        mAppBarConfiguration = AppBarConfiguration(mNavController.graph)

        if (savedInstanceState == null) {
            val navGraph = mNavController.navInflater.inflate(R.navigation.main_navigation).apply {
                setStartDestination(
                    if (AppSharedMethods.isLogin()) {
                        R.id.shoesListFragment
                    } else {
                        R.id.loginFragment
                    }
                )
            }
            mNavController.graph = navGraph
        }

        initViewModelObservers()
    }

    private fun initViewModelObservers() {
        with(mMainViewModel) {
            hideToolbarLiveData.observe(this@MainActivity) {
                mBinding.mainToolbar.visibility = if (it) View.GONE else View.VISIBLE
            }
            toolbarTitleLiveData.observe(this@MainActivity) {
                mBinding.textViewToolbarTitle.text = it
            }
            showUpButtonLiveData.observe(this@MainActivity) {
                supportActionBar?.setDisplayHomeAsUpEnabled(it)
            }
            navigationCommand.observe(this@MainActivity) { command ->
                Timber.d("initViewModelObserver:command: $command")
                when (command) {
                    is NavigationCommand.To -> mNavController.navigate(command.directions)
                    is NavigationCommand.Back -> mNavController.popBackStack()
                    is NavigationCommand.BackTo -> mNavController.popBackStack(
                        command.destinationId, false
                    )
                }
            }
            showToast.observe(this@MainActivity) {
                AppSharedMethods.showToast(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
    }

}
