package com.getsporttrade.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.getsporttrade.assignment.databinding.ActivityMainBinding
import com.getsporttrade.assignment.ui.BaseFragmentFactory
import com.getsporttrade.assignment.ui.fragment.PositionListFragment
import com.kosoku.kirby.fragment.NavigationFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity in a single-activity app. Loads the [PositionListFragment] into its
 * content container onCreate.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = BaseFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .apply {
                replace(binding.containerView.id, NavigationFragment.getInstance(
                    PositionListFragment.getInstance()))
                commit()
            }
    }
}