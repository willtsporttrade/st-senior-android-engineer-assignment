package com.getsporttrade.assignment.service.network

import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.service.network.response.PositionsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit Interface for each of the endpoints available to the service
 */
interface NetworkService {
    /**
     * GET a single Position object from the network
     */
    @GET(Endpoint.GET_POSITION_PATH)
    @EndpointIdentifier(Endpoint.GET_POSITION)
    fun getPosition(@Path("identifier") identifier: String): Single<Position>

    /**
     * GET all Position objects from the network
     */
    @GET(Endpoint.GET_POSITIONS_PATH)
    @EndpointIdentifier(Endpoint.GET_POSITIONS)
    fun getPositions(): Single<PositionsResponse>
}