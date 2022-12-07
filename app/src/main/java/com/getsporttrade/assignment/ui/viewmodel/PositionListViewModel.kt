package com.getsporttrade.assignment.ui.viewmodel

import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * [BaseViewModel] subclass for interfacing with the list of positions through the repository layer
 */
@HiltViewModel
class PositionListViewModel @Inject constructor(
    private val positionRepository: PositionRepository
) : BaseViewModel()
