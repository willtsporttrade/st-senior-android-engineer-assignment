package com.getsporttrade.assignment.ui

import com.kosoku.kirby.fragment.KBYFragment

/**
 * Abstract class for fragments in this application that reference an optional [BaseViewModel]
 * and clear any rxDisposables onStop, that might exist
 */
abstract class BaseFragment : KBYFragment() {
    override val viewModel: BaseViewModel? = null

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}