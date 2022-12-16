package com.getsporttrade.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * [BaseViewModel] subclass for interfacing with the list of positions through the repository layer
 */
@HiltViewModel
class PositionListViewModel @Inject constructor(
    private val positionRepository: PositionRepository
) : BaseViewModel() {

    /**
     * The internal position list result mutable live data which will be updated when repository emits
     * Flowable that contains list of position items
     */
    private val _positions = MutableLiveData<Result<List<Position>>>()

    /**
     * The observable position list result live data which will be updated when its associated mutable
     * live data value changes
     */
    val positions: LiveData<Result<List<Position>>> = _positions
    private val disposable: Disposable

    init {
        disposable = observePositions()
        disposables.add(disposable)
    }

    /**
     * Subscribes to the rxFlowable of position list and updates live data result list
     *
     * @throws Throwable subscription error
     */
    private fun observePositions() = positionRepository.observePositions()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            _positions.value = Result.success(it)
        }, {
            _positions.value = Result.failure(it)
        })
}
