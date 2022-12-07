package com.getsporttrade.assignment.repository

import com.getsporttrade.assignment.extension.persist
import com.getsporttrade.assignment.service.cache.LocalDatabase
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.service.network.NetworkService
import com.getsporttrade.assignment.utils.value
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Repository layer for interacting with position model objects
 */
class PositionRepository @Inject constructor(
    private val networkService: NetworkService,
    private val dbService: LocalDatabase
) {
    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    private val positionIdentifierSubject = BehaviorSubject.create<String>()

    /**
     * Observe an rxFlowable list of Positions. Returns existing items in the local cache, and
     * imports any changes from the network which get passed on for as long as the subscription
     * is maintained.
     *
     * @return rxFlowable A list of positions
     */
    fun observePositions(): Flowable<List<Position>> {
        importPositions()
        return dbService.positionDao().getPositions()
    }

    /**
     * Observe an rxFlowable for an individual Positions. Returns the existing item from the
     * local cache, and imports any changes from the network which get passed on for as
     * long as the subscription is maintained.
     *
     * @return rxFlowable A single position
     */
    fun observePosition(identifier: String): Flowable<Position> {
        updatePosition(identifier)
        return dbService.positionDao().getPosition(identifier)
            .filter { it.isNotEmpty() }
            .map { it.first() }
    }

    /**
     * Private method for importing position updates from the network.
     */
    private fun importPositions() {
        networkService.getPositions()
            .flatMapCompletable {
                it.positions.persist(dbService.positionDao()).ignoreElement()
            }
            .subscribeOn(Schedulers.io())
            .subscribe().addTo(disposables)
    }

    /**
     * Private method for triggering regular updates to the individual position being observed.
     */
    private fun updatePosition(identifier: String) {
        positionIdentifierSubject.onNext(identifier)
    }

    /**
     * Class initializer that kicks of an observable to fetch position updates every 5 seconds
     * for the individual position currently being observed.
     */
    init {
        positionIdentifierSubject.toFlowable(BackpressureStrategy.LATEST)
            .combineLatest(Observable.interval(5, TimeUnit.SECONDS).toFlowable(BackpressureStrategy.LATEST))
            .flatMapSingle { (identifier, _) ->
                networkService.getPosition(identifier)
                    .flatMap { networkPosition ->
                        dbService.positionDao().fetch(identifier)
                            .flatMap {
                                val cachedPosition = it.value
                                if (cachedPosition == null) {
                                    networkPosition
                                } else {
                                    Position(
                                        identifier = cachedPosition.identifier,
                                        name = cachedPosition.name,
                                        criteriaName = cachedPosition.criteriaName,
                                        storyName = cachedPosition.storyName,
                                        price = networkPosition.price,
                                        quantity = networkPosition.quantity
                                    )
                                }.persist(dbService.positionDao())
                            }
                    }
            }
            .subscribeOn(Schedulers.io())
            .subscribe().addTo(disposables)
    }
}