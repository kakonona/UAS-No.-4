// Retrofit Client untuk konfigurasi Retrofit
// Taruh di: app/src/main/java/com/example/uas/network/RetrofitClient.kt

package com.example.uasno4.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL API (menggunakan JSONPlaceholder sebagai contoh)
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Lazy initialization Retrofit instance
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}