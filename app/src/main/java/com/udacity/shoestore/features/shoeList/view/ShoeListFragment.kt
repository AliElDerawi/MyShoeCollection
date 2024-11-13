package com.udacity.shoestore.features.shoeList.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.shoeList.adapter.ItemBookmarkShoeAdapter
import com.udacity.shoestore.features.shoeList.viewModel.ShoeListViewModel
import com.udacity.shoestore.models.ShoeModel
import com.udacity.shoestore.utils.AppSharedData
import com.udacity.shoestore.utils.AppSharedMethods.getSharedPreference
import com.udacity.shoestore.utils.AppSharedMethods.setLoginStatus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoeListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentShoeListBinding
    private val mSharedViewModel: MainViewModel by inject()
    override val mViewModel: ShoeListViewModel by viewModel()
    private lateinit var mActivity: FragmentActivity
    private lateinit var mLifecycleOwner: LifecycleOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentShoeListBinding.inflate(inflater, container, false).apply {
            mLifecycleOwner = viewLifecycleOwner
            lifecycleOwner = mLifecycleOwner
            sharedViewModel = mSharedViewModel
            shoeListFragment = this@ShoeListFragment
        }
        mSharedViewModel.apply {
            setHideToolbar(false)
            setToolbarTitle(getString(R.string.text_bookmarked_shoes))
            showUpButton(false)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        mBinding.shoesListRecyclerView.adapter =
            ItemBookmarkShoeAdapter(ShoeModel.getShoeModelCallback()) { shoeModel ->
                mSharedViewModel.showToast.value =
                    "Handling Click in Generic List Adapter: ${shoeModel.name}"
            }
    }

    private fun initMenu() {

        val menuHost: MenuHost = mActivity

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // TODO Comment : We can use NavigationUI.onNavDestinationSelected() to handle the navigation
//                return NavigationUI.onNavDestinationSelected(menuItem, requireView().findNavController())
                if (menuItem.itemId == R.id.loginFragment) {
                    mSharedViewModel.setHideToolbar(true)
                    setLoginStatus(false)
                    mSharedViewModel.navigationCommand.value = NavigationCommand.To(
                        ShoeListFragmentDirections.actionShoesListFragmentToLoginFragment()
                    )
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun onAddShoeClick() {
        mSharedViewModel.navigationCommand.value = NavigationCommand.To(
            ShoeListFragmentDirections.actionShoesListFragmentToShoeDetailFragment()
        )
    }

//
//    fun addViewToViewGroup(viewGroup: ViewGroup, shoeModel: ShoeModel) {
//        val inflater = LayoutInflater.from(viewGroup.context)
//        val binding = ItemBookmarkedShoeBinding.inflate(inflater, viewGroup, true)
//        binding.shoe = shoeModel
//        binding.executePendingBindings()
//    }

}