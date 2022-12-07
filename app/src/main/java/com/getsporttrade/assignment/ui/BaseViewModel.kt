package com.getsporttrade.assignment.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * [ViewModel] subclass that includes a lazy reference to a [CompositeDisposable] and clears
 * those disposables when the viewmodel itself is cleared
 */
abstract class BaseViewModel : ViewModel() {
    open val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}