package com.getsporttrade.assignment.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.getsporttrade.assignment.ui.fragment.PositionDetailsFragment
import com.getsporttrade.assignment.ui.fragment.PositionListFragment

/**
 * FragmentFactory for creating the appropriate fragment when contained inside a [NavigationFragment]
 */
class BaseFragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PositionListFragment::class.java.name -> PositionListFragment()
            PositionDetailsFragment::class.java.name -> PositionDetailsFragment()

            else -> super.instantiate(classLoader, className)
        }
    }
}