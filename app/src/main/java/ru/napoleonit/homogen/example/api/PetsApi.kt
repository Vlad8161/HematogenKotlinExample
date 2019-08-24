package ru.napoleonit.homogen.example.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import ru.napoleonit.homogen.example.operations.CreatePets
import ru.napoleonit.homogen.example.operations.GetListPets


class PetsApi(
    moshi: Moshi,
    client: OkHttpClient
) {
    private val getListPets = GetListPets(moshi, client)
    private val createPets = CreatePets(moshi, client)

    fun getListPets(block: GetListPets.Request.Builder.() -> Unit = {}): GetListPets.Response {
        return getListPets.execute(
            GetListPets.Request.Builder()
                .apply(block)
                .build()
        )
    }

    fun createPets(): CreatePets.Response {
        return createPets.execute()
    }
}
