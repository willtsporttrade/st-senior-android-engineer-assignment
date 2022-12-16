package com.getsporttrade.assignment.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.getsporttrade.assignment.BaseTest
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PositionListViewModelTest : BaseTest() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val positionRepo = mockk<PositionRepository>()
    private val positionList = (1..2).map {
        Position(
            identifier = "id-$it",
            name = "Name $it",
            criteriaName = "Criteria $it",
            storyName = "Story $it",
            price = it.toBigDecimal(),
            quantity = it.toBigDecimal()
        )
    }

    private lateinit var viewModel: PositionListViewModel

    @Before
    fun setUp() {
        every { positionRepo.observePositions() } returns Flowable.just(positionList)
        viewModel = PositionListViewModel(positionRepo)
    }

    @Test
    fun `verify observed position list is not null`() {
        viewModel.positions.observeForever {
            assertNotNull(it)
        }
    }

    @Test
    fun `verify observed position list is not empty`() {
        viewModel.positions.observeForever {
            val positions = it.getOrDefault(emptyList())
            assertFalse(positions.isEmpty())
        }
    }

    @Test
    fun `observing position list should return positions`() {
        viewModel.positions.observeForever {
            val positions = it.getOrDefault(emptyList())
            assertEquals(positionList, positions)
        }
    }
}