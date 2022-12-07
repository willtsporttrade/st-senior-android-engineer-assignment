package com.getsporttrade.assignment.service.cache.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.getsporttrade.assignment.service.cache.entity.EntityIdentifiable
import com.getsporttrade.assignment.utils.None
import com.getsporttrade.assignment.utils.Optional
import com.getsporttrade.assignment.utils.Some
import com.getsporttrade.assignment.utils.value
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Abstract Dao class for handling deletion, insertion, or update of entity objects.
 */
abstract class BaseDao<T : EntityIdentifiable> (
    private val tableName: String
) {
    /**
     * Internal method for passing raw SQL queries to the database.
     */
    @RawQuery
    protected abstract fun fetch(query: SupportSQLiteQuery): Single<List<T>>

    /**
     * Delete an object from database
     *
     * @param obj the object to be deleted
     * @return Rx Completable
     */
    @Delete
    abstract fun delete(obj: T): Completable

    /**
     * Delete a list of objects from database
     *
     * @param obj the objects to be deleted
     * @return Rx Completable
     */
    @Delete
    abstract fun delete(obj: List<T>): Completable

    /**
     * Insert an object into database
     *
     * @param obj the object to be inserted
     * @return rxSingle of the record ID for the object inserted (-1L if not inserted)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: T): Single<Long>

    /**
     * Insert a list of objects into database
     *
     * @param obj list of objects to be inserted
     * @return rxSingle list of the record IDs for the objects inserted (-1L if not inserted)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: List<T>): Single<List<Long>>

    /**
     * Update an object in the database
     *
     * @param obj the object to be updated
     * @return rxSingle with the number of records updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(obj: T): Single<Int>

    /**
     * Update a list of objects in the database
     *
     * @param obj list of objects to be updated
     * @return rxSingle with the number of records updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(obj: List<T>): Single<Int>

    /**
     * Fetch a single, optional entity from the database by its identifier
     *
     * @param identifier The primary key identifier for the entity
     * @return rxSingle An optional value for the item to fetch
     */
    open fun fetch(identifier: String): Single<Optional<T>> = fetch(listOf(identifier)).map {
        if (it.isEmpty()) {
            None
        } else {
            Some(it.first())
        }
    }

    /**
     * Fetch a list of entities from the database by their identifiers
     *
     * @param identifiers The primary key identifier for the entity
     * @return rxSingle A list of entities
     */
    open fun fetch(identifiers: List<String>): Single<List<T>> {
        val formattedList = identifiers.joinToString(",")
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE identifier IN ('$formattedList')")
        return fetch(query)
    }

    /**
     * Fetch a single entity and insert it if it doesn't exist
     *
     * @param obj The entity to fetch or insert
     * @return rxSingle The entity saved to the database
     */
    open fun fetchOrInsert(obj: T): Single<T> {
        return insert(obj)
            .flatMap { row ->
                return@flatMap if (row == -1L) {
                    fetch(obj.identifier).map { it.value!! }
                } else {
                    Single.just(obj)
                }
            }
    }

    /**
     * Try to insert an entity and fallback to update if the entity already exists. Method can be subclassed for custom implementation by entity.
     *
     * @param obj the object to insert or update
     * @return rxSingle with object inserted or updated
     */
    open fun insertOrUpdate(obj: T): Single<T> {
        return insert(obj)
            .flatMap { row ->
                return@flatMap if (row == -1L) {
                    update(obj).map { obj }
                } else {
                    Single.just(obj)
                }
            }
    }

    /**
     * Try to insert a list of entities and fallback to update if the entity already exists. Method can be subclassed for custom implementation by entity.
     *
     * @param obj list objects to insert or update
     * @return rxSingle list with the objects inserted or updated
     */
    open fun insertOrUpdate(obj: List<T>): Single<List<T>> {
        return insert(obj)
            .flatMap { rows ->
                val objectsToUpdate = rows.mapIndexedNotNull { index, row ->
                    if (row != -1L) null else obj[index]
                }
                return@flatMap Single.just(objectsToUpdate)
            }.flatMap { update(it) }
            .map { obj }
    }
}