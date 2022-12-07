package com.getsporttrade.assignment.service.cache.entity

import org.json.JSONObject

/**
 * Interface for ensuring the conforming class can provide a JSONObject stub.
 */
interface EntityStubbable {
    fun stub(identifier: String): JSONObject
}