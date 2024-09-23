package com.udacity.shoestore.data

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/**
 * Base Fragment to observe on the common LiveData objects
 */
abstract class BaseFragment : Fragment() {

    /**
     * Every fragment has to have an instance of a view model that extends from the BaseViewModel
     */
    abstract val mViewModel: BaseViewModel

    private lateinit var mActivity : FragmentActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    override fun onStart() {
        super.onStart()

    }


    private fun showWaiteDialog() {

    }

    private fun hideWaiteDialog() {

    }
}