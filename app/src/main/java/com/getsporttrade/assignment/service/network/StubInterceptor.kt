package com.getsporttrade.assignment.service.network

import android.content.Context
import com.getsporttrade.assignment.service.cache.entity.Position
import com.getsporttrade.assignment.service.network.response.PositionsResponse
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Invocation
import timber.log.Timber
import java.util.*

/**
 * Custom OKHttp Interceptor class for returning stub data in a network request
 */
class StubInterceptor : Interceptor {

    /**
     * Intercepts the network request and returns stub data if the endpoint in question
     * is capable of producing data stubs
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        request.tag(Invocation::class.java)?.let {
            it.method().getAnnotation(EndpointIdentifier::class.java)?.let { identifier ->
                val stubData = when (identifier.value) {
                    Endpoint.GET_POSITION -> Position.stub(request.url.pathSegments.lastOrNull() ?: UUID.randomUUID().toString()).toString()
                    Endpoint.GET_POSITIONS -> PositionsResponse.stub().toString()
                }
                return Response.Builder()
                    .request(request)
                    .code(200)
                    .protocol(Protocol.HTTP_2)
                    .message(stubData)
                    .body(stubData.toResponseBody("application/json".toMediaType()))
                    .addHeader("content-type","application/json")
                    .build()
            }
        }

        return chain.proceed(request)
    }
}