package com.example.clothesapp.model

data class ReviewOrderClothesItem(
    val Size: String,
    val _id: String,
    val image: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val size_l: Int,
    val size_m: Int,
    val size_s: Int,
    val size_xl: Int
)