package ru.napoleonit.homogen.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import ru.napoleonit.homogen.example.api.PetsApi
import ru.napoleonit.homogen.example.operations.GetListPets

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient()
        val moshi = Moshi.Builder().build()
        val petsApi = PetsApi(moshi, okHttpClient)

        val resp1 = petsApi.getListPets {
            params {
                limit = 2
            }
        }

        val resp2 = petsApi.getListPets()

        val resp3 = petsApi.createPets()

        when (resp1) {
            is GetListPets.Response.Code200 -> {
                Toast.makeText(this, "It's fine", Toast.LENGTH_SHORT).show()
            }
            is GetListPets.Response.Default ->
                Toast.makeText(this, "It's fine", Toast.LENGTH_SHORT).show()
        }
    }
}


