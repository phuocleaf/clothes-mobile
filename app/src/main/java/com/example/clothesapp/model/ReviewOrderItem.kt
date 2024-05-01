package com.example.clothesapp.model

data class ReviewOrderItem(
    val _id: String,
    val status: String,
    val total: Int,
    val userAddress: String,
    val userName: String,
    val userNote: String,
    val userPhone: String,
    val created_at: String
)