package com.getsporttrade.assignment.service.network.response

import org.json.JSONObject

/**
 * Interface for ensuring the conforming class can provide a JSONObject stub.
 */
interface ResponseStubbable {
    fun stub(): JSONObject
}