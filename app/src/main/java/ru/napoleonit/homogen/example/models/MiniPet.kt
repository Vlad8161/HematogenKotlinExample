package ru.napoleonit.homogen.example.models

import com.squareup.moshi.Json


class MiniPet(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String
)

