package com.getsporttrade.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.getsporttrade.assignment.R
import com.getsporttrade.assignment.databinding.FragmentPositionListBinding
import com.getsporttrade.assignment.extension.showSnackbar
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.ui.BaseFragment
import com.getsporttrade.assignment.ui.viewmodel.PositionListViewModel
import com.getsporttrade.assignment.ui.widget.PositionListAdapter
import com.kosoku.kirby.fragment.NavigationFragment
import dagger.hilt.android.AndroidEntryPoint

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

    /**
     * The [PositionListAdapter] recycler view adapter to display position list which expects position item
     * click callback parameter to navigate to the position detail [PositionDetailsFragment] screen
     */
    private val listAdapter by lazy {
        PositionListAdapter {
            navigationController?.get()?.pushFragment(PositionDetailsFragment.getInstance(position = it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_position_list, container, false)
        (binding as? FragmentPositionListBinding)?.let {
            with(it.recyclerView) {
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                adapter = listAdapter
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.positions.observe(viewLifecycleOwner, ::onPositionListChanged)
    }

    /**
     * Observes position list result live data change and updates recycler view adapter so current
     * fragment can show position list items. When it fails; it will show error message snackbar
     *
     * @param result position list result
     */
    private fun onPositionListChanged(result: Result<List<Position>>?) =
        result?.let {
            when {
                it.isFailure -> showSnackbar(it.exceptionOrNull()?.message.orEmpty(), requireView())
                it.isSuccess -> listAdapter.submitList(it.getOrDefault(emptyList()))
            }
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