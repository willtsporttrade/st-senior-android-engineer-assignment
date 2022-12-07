package com.getsporttrade.assignment.service.network

/**
 * Custom annotation class for parsing application-specific information from a networking endpoint
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointIdentifier(val value: Endpoint)

/**
 * Enum class representing different endpoints available in the [NetworkService]
 */
enum class Endpoint {
    GET_POSITIONS,
    GET_POSITION;

    companion object {
        const val GET_POSITIONS_PATH = "positions"
        const val GET_POSITION_PATH = "position/{identifier}"
    }
}