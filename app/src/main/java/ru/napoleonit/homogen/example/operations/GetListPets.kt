package ru.napoleonit.homogen.example.operations

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import ru.napoleonit.homogen.example.models.Error
import ru.napoleonit.homogen.example.models.Pet
import ru.napoleonit.homogen.example.ServerConfig


class GetListPets(
    private val moshi: Moshi,
    private val client: OkHttpClient
) {

    fun execute(request: Request): Response {
        val okHttpRequest = okhttp3.Request.Builder()
            .get()
            .url(
                HttpUrl.parse("${ServerConfig.url}/pets")!!.newBuilder().apply {
                    if (request.parameters?.limit != null) {
                        addQueryParameter("limit", request.parameters.limit.toString())
                    }
                }.build()
            )
            .build()
        val okHttpResponse = client.newCall(okHttpRequest).execute()
        return when (okHttpResponse.code()) {
            200 -> Response.Code200(
                headers = Response.Code200.Headers(
                    xNext = okHttpResponse.header("x-next")!!
                ),
                content = moshi.adapter<List<Pet>>(
                    Types.newParameterizedType(List::class.java, Pet::class.java)
                    // TODO: exception handling
                ).fromJson(okHttpResponse.body()!!.source())!!
            )
            else -> Response.Default(
                code = okHttpResponse.code(),
                content = moshi.adapter<Error>(Error::class.java)
                    // TODO: exception handling
                    .fromJson(okHttpResponse.body()!!.source())!!
            )
        }
    }

    class Request(
        val parameters: Parameters?
    ) {
        class Builder {
            private var parameters: Parameters? = null

            fun params(block: Parameters.Builder.() -> Unit) {
                parameters = Parameters.Builder()
                    .apply(block)
                    .build()
            }

            fun build(): Request {
                return Request(
                    parameters = parameters
                )
            }
        }

        class Parameters(
            val limit: Int?
        ) {
            class Builder {
                var limit: Int? = null

                fun build(): Parameters {
                    return Parameters(
                        limit
                    )
                }
            }
        }
    }

    sealed class Response {
        abstract val code: Int

        class Code200(
            val headers: Headers,
            val content: List<Pet>
        ) : Response() {
            override val code: Int = 200

            class Headers(
                val xNext: String
            )
        }

        class Default(
            override val code: Int,
            val content: Error
        ) : Response()
    }
}