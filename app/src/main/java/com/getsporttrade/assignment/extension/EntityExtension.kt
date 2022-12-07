package com.getsporttrade.assignment.extension

import com.getsporttrade.assignment.service.cache.dao.BaseDao
import com.getsporttrade.assignment.service.cache.entity.EntityIdentifiable
import io.reactivex.rxjava3.core.Single

/**
 * Generic extensions for performing insertOrUpdate on entity models
 * conforming to [EntityIdentifiable]
 */

/**
 * Persist a single entity
 *
 * @param dao the DAO for persisting the entity
 * @return rxSingle returning the newly saved entity object
 */
fun <T> T.persist(dao: BaseDao<T>): Single<T> where T : EntityIdentifiable {
    return dao.insertOrUpdate(this)
}

/**
 * Persist a list of entities
 *
 * @param dao the DAO for persisting the list of entities
 * @return rxSingle returning a list of the newly saved entity objects
 */
fun <T> List<T>.persist(dao: BaseDao<T>): Single<List<T>> where T : EntityIdentifiable {
    return dao.insertOrUpdate(this)
}

/**
 * Persist a set of entities
 *
 * @param dao the DAO for persisting the set of entities
 * @return rxSingle returning a list of the newly saved entity objects
 */
fun <T> Set<T>.persist(dao: BaseDao<T>): Single<List<T>> where T : EntityIdentifiable {
    return dao.insertOrUpdate(this.toList())
}