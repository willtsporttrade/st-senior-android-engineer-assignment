package com.getsporttrade.assignment.service.network.response

import com.getsporttrade.assignment.service.cache.entity.Position
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Response model object for handling calls to fetch positions from the network
 */
data class PositionsResponse(
    val positions: List<Position>
) {
    companion object : ResponseStubbable {

        /**
         * Convenience method for creating a random set of position response objects
         */
        override fun stub(): JSONObject {
            val positionStubs = JSONArray()
            (1..25).forEach { _ ->
                positionStubs.put(Position.stub(UUID.randomUUID().toString()))
            }
            return JSONObject().apply {
                put("positions", positionStubs)
            }
        }
    }
}