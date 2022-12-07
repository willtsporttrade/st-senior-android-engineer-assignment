package com.getsporttrade.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.getsporttrade.assignment.R
import com.getsporttrade.assignment.ui.BaseFragment
import com.getsporttrade.assignment.ui.viewmodel.PositionDetailsViewModel
import com.getsporttrade.assignment.ui.viewmodel.PositionListViewModel
import com.kosoku.kirby.fragment.NavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * [BaseFragment] subclass for displaying a list of Positions
 */
@AndroidEntryPoint
class PositionListFragment : BaseFragment() {

    /**
     * The title string to be displayed by the [NavigationFragment]'s toolbar
     */
    override val title: String
        get() = getString(R.string.position_list_screen_title)

    /**
     * The [BaseViewModel] subclass for position details
     */
    override val viewModel: PositionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_position_list, container, false)

        /*
        To handle selection of the position item, call the following to push the details fragment onto the view stack

        navigationController?.get()?.pushFragment(PositionDetailsFragment.getInstance(<selected-position>))
         */

        return binding?.root
    }

    companion object {
        /**
         * Instance generator that allows the [BaseFragmentFactory] to reproduce any
         * attributes in the bundle when the system creates the fragment itself
         */
        fun getInstance(): PositionListFragment {
            return PositionListFragment().getInstance() as PositionListFragment
        }
    }
}