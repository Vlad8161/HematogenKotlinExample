package ru.napoleonit.homogen.example.operations

import com.squareup.moshi.Moshi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import ru.napoleonit.homogen.example.Error
import ru.napoleonit.homogen.example.ServerConfig


class CreatePets(
    private val moshi: Moshi,
    private val client: OkHttpClient
) {

    fun execute(): Response {
        val okHttpRequest = okhttp3.Request.Builder()
            .post(RequestBody.create(null, ByteArray(0)))
            .url(
                HttpUrl.parse("${ServerConfig.url}/pets")!!.newBuilder().apply {
                }.build()
            )
            .build()
        val okHttpResponse = client.newCall(okHttpRequest).execute()
        return when (okHttpResponse.code()) {
            201 -> Response.Code201()
            else -> Response.Default(
                code = okHttpResponse.code(),
                content = moshi.adapter<Error>(Error::class.java)
                    // TODO: exception handling
                    .fromJson(okHttpResponse.body()!!.source())!!
            )
        }
    }

    sealed class Response {
        abstract val code: Int

        class Code201 : Response() {
            override val code: Int = 201
        }

        class Default(
            override val code: Int,
            val content: Error
        ) : Response()
    }
}