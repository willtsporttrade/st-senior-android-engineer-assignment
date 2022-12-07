package com.getsporttrade.assignment.service.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.getsporttrade.assignment.service.cache.entity.Position
import io.reactivex.rxjava3.core.Flowable

/**
 * DAO for interacting with Position entities in the database
 */
@Dao
abstract class PositionDao : BaseDao<Position>(Position.TABLE_NAME) {
    /**
     * Observe an rxFlowable list of all positions in the database. This will continue to vend
     * entities anytime there are updates to the local cache.
     *
     * @return rxFlowable A list of positions
     */
    @Transaction
    @Query("SELECT * FROM ${Position.TABLE_NAME}")
    abstract fun getPositions(): Flowable<List<Position>>

    /**
     * Observe an rxFlowable list of all positions in the database. This will continue to vend
     * entities anytime there are updates to the local cache.
     *
     * *Note - returns a list to avoid breaking the flowable if the entity does not exist.
     * You should filter the list and take the first item if the list is not empty.
     *
     * @return rxFlowable A list of positions
     */
    @Transaction
    @Query("SELECT * FROM ${Position.TABLE_NAME} WHERE identifier = :identifier")
    abstract fun getPosition(identifier: String): Flowable<List<Position>>
}