package com.getsporttrade.assignment.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [BaseViewModel] subclass for interfacing with the position details through the repository layer
 */
@HiltViewModel
class PositionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val positionRepository: PositionRepository
) : BaseViewModel() {
    private val positionIdentifier: String?

    companion object {
        /**
         * Key representing the position identifier in the fragment bundle
         */
        const val POSITION_IDENTIFIER_KEY = "positionIdentifierKey"
    }

    /**
     * Custom initializer that extracts the position identifier from the fragment's
     * saved state bundle
     */
    init {
        positionIdentifier = savedStateHandle[POSITION_IDENTIFIER_KEY]
    }
}
