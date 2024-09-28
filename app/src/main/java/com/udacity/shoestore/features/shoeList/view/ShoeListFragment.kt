package com.udacity.shoestore.features.shoeList.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.udacity.shoestore.R
import com.udacity.shoestore.data.BaseFragment
import com.udacity.shoestore.data.NavigationCommand
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.features.main.viewModel.MainViewModel
import com.udacity.shoestore.features.shoeList.adapter.ItemBookmarkShoeAdapter
import com.udacity.shoestore.features.shoeList.viewModel.ShoeListViewModel
import com.udacity.shoestore.models.ShoeModel
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentShoeListBinding.inflate(inflater, container, false)
        mSharedViewModel.setHideToolbar(false)
        mLifecycleOwner = viewLifecycleOwner
        mBinding.sharedViewModel = mSharedViewModel
        mBinding.lifecycleOwner = this
        mBinding.shoeListFragment = this
        setHasOptionsMenu(true)
        mSharedViewModel.setToolbarTitle(mActivity.getString(R.string.text_bookmarked_shoes))
        mSharedViewModel.showUpButton(false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViewModelObserver()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        mBinding.shoesListRecyclerView.adapter =
            ItemBookmarkShoeAdapter(ShoeModel.getShoeModelCallback(), { shoeModel ->
                mSharedViewModel.showToast.value = "Handling Click in Generic List Adapter"
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            mSharedViewModel.setHideToolbar(true)
            mSharedViewModel.navigationCommand.value =
                NavigationCommand.To(ShoeListFragmentDirections.actionShoesListFragmentToLoginFragment())
        }
        return true
    }

    fun onAddShoeClick() {
        mSharedViewModel.navigationCommand.value =
            NavigationCommand.To(ShoeListFragmentDirections.actionShoesListFragmentToShoeDetailFragment())
    }

//
//    fun addViewToViewGroup(viewGroup: ViewGroup, shoeModel: ShoeModel) {
//        val inflater = LayoutInflater.from(viewGroup.context)
//        val binding = ItemBookmarkedShoeBinding.inflate(inflater, viewGroup, true)
//        binding.shoe = shoeModel
//        binding.executePendingBindings()
//    }


}