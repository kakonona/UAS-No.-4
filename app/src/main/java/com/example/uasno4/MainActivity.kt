package com.example.uasno4

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data class untuk response
data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
)

// Interface API
interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

class MainActivity : AppCompatActivity() {

    private val TAG = "RetrofitDemo"
    private lateinit var btnFetch: Button
    private lateinit var tvResult: TextView

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFetch = findViewById(R.id.btnFetch)
        tvResult = findViewById(R.id.tvResult)

        btnFetch.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        tvResult.text = "Loading..."
        btnFetch.isEnabled = false

        lifecycleScope.launch {
            try {
                Log.d(TAG, "Mulai di thread: ${Thread.currentThread().name}")

                // Network request di IO thread
                val posts = withContext(Dispatchers.IO) {
                    Log.d(TAG, "Network di thread: ${Thread.currentThread().name}")
                    apiService.getPosts()
                }

                Log.d(TAG, "Kembali ke thread: ${Thread.currentThread().name}")
                Log.d(TAG, "Data: ${posts.size} posts")

                // Update UI
                val result = StringBuilder()
                result.append("Berhasil ambil ${posts.size} posts\n\n")

                posts.take(5).forEach { post ->
                    result.append("ID: ${post.id}\n")
                    result.append("Title: ${post.title}\n")
                    result.append("Body: ${post.body.take(50)}...\n\n")
                }

                tvResult.text = result.toString()
                Toast.makeText(this@MainActivity, "Berhasil!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                tvResult.text = "Error: ${e.message}"
                Toast.makeText(this@MainActivity, "Gagal!", Toast.LENGTH_SHORT).show()
            } finally {
                btnFetch.isEnabled = true
            }
        }
    }
}