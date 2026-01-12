// Interface Retrofit untuk GET data dari API
// Taruh di: app/src/main/java/com/example/uas/network/ApiService.kt

package com.example.uasno4.network

import com.example.uasno4.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // GET semua posts
    @GET("posts")
    suspend fun getPosts(): List<Post>

    // GET post berdasarkan ID
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post
}