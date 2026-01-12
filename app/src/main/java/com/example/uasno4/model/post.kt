// Data class untuk response API
// Taruh di: app/src/main/java/com/example/uas/model/Post.kt

package com.example.uasno4.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)