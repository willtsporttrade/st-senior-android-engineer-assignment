package com.getsporttrade.assignment.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.getsporttrade.assignment.BaseTest
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal

class PositionDetailsViewModelTest : BaseTest() {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val savedStateHandle = mockk<SavedStateHandle>()
    private val positionRepo = mockk<PositionRepository>()
    private val positionId = "100"
    private val position = Position(
        identifier = positionId,
        name = "Name",
        criteriaName = "Criteria name",
        storyName = "Story name",
        price = BigDecimal.TEN,
        quantity = BigDecimal.ONE
    )

    private lateinit var viewModel: PositionDetailsViewModel

    @Before
    fun setUp() {
        every { savedStateHandle.get<String>(PositionDetailsViewModel.POSITION_IDENTIFIER_KEY) } returns positionId
        every { positionRepo.observePosition(identifier = positionId) } returns Flowable.just(
            position
        )

        viewModel = PositionDetailsViewModel(
            savedStateHandle = savedStateHandle,
            positionRepository = positionRepo
        )
    }

    @Test
    fun `verify observed result value is not null`() {
        viewModel.positionResult.observeForever {
            assertNotNull(it)
        }
    }

    @Test
    fun `verify observed position value is not null`() {
        viewModel.positionResult.observeForever {
            val pos = it.getOrNull()
            assertNotNull(pos)
        }
    }

    @Test
    fun `verify observed position value`() {
        viewModel.positionResult.observeForever {
            val pos = it.getOrNull()
            assertEquals(position, pos)
        }
    }
}