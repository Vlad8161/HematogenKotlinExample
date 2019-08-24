package ru.napoleonit.homogen.example.models

import com.squareup.moshi.Json


class Pet(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "tag")
    val tag: String
)

