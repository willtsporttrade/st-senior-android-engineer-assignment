package com.getsporttrade.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
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

    /**
     * The private position mutable live data which will be updated when repository emits
     * position [Position] Flowable value wrapped in result [Result] helper
     */
    private val _positionResult = MutableLiveData<Result<Position>>()

    /**
     * The position observable live data which will be updated when its mutable value gets updated
     */
    val positionResult: LiveData<Result<Position>> = _positionResult

    /**
     * The position live data which position detail fragment view (xml layout) system will use to
     * update once observed position result value changes
     */
    val position: LiveData<Position?> = Transformations.map(positionResult) {
        when {
            it.isSuccess -> it.getOrNull()
            else -> null
        }
    }

    private lateinit var disposable: Disposable

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
        positionIdentifier?.let {
            disposable = observePosition(id = it)
            disposables.add(disposable)
        }
    }

    /**
     * Fetches position data based on its identifier and updates live data once observed Flowable
     * emits position [Position] item
     *
     * @param id target position [Position] identifier
     * @throws Throwable when subscriber receives error [Throwable]
     */
    private fun observePosition(id: String) = positionRepository.observePosition(identifier = id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            _positionResult.value = Result.success(it)
        }, {
            _positionResult.value = Result.failure(it)
        })
}
