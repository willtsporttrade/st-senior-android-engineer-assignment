package com.getsporttrade.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.getsporttrade.assignment.R
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.ui.BaseFragment
import com.getsporttrade.assignment.ui.viewmodel.PositionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * [BaseFragment] subclass for displaying the detail information for a single Position
 */
@AndroidEntryPoint
class PositionDetailsFragment : BaseFragment() {

    /**
     * The [BaseViewModel] subclass for position details
     */
    override val viewModel: PositionDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_position_details, container, false)

        return binding?.root
    }

    companion object {
        /**
         * Instance generator that allows the [BaseFragmentFactory] to reproduce any
         * attributes in the bundle when the system creates the fragment itself
         */
        fun getInstance(position: Position): PositionDetailsFragment {
            return PositionDetailsFragment().getInstance(
                bundleOf(
                    PositionDetailsViewModel.POSITION_IDENTIFIER_KEY to position.identifier
                )
            ) as PositionDetailsFragment
        }
    }
}