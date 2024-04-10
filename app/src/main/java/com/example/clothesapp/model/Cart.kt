package com.example.clothesapp.model

data class Cart(
    val _id: String,
    val image: String,
    val name: String,
    val price: Int,
    val size_l: Int,
    val size_m: Int,
    val size_s: Int,
    val size_xl: Int,
    var quantity: Int,
    var Size: String
)
