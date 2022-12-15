package com.getsporttrade.assignment.dependency

import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.entity.Position
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {

    companion object {
        private val positions = (1..3).map {
            Position(
                identifier = "id-$it",
                name = "Name $it",
                criteriaName = "Criteria name $it",
                storyName = "Story name $it",
                price = it.toBigDecimal(),
                quantity = it.toBigDecimal()
            )
        }

        @Singleton
        @Provides
        fun providePositionRepository(): PositionRepository {
            val repo = mockk<PositionRepository>()
            val flowablePositionList = Flowable.just(positions)
            val flowablePosition = Flowable.just(positions.first())
            every { repo.observePositions() } returns flowablePositionList
            every { repo.observePosition("id-1") } returns flowablePosition
            return repo
        }
    }
}