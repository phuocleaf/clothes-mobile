package com.example.clothesapp.model

data class CreateOrderData(
    val userId : String,
    val userAddress: String,//
    val userName: String,//
    val userPhone: String,//
    val cartList: List<Cart>,//
    val total: Int,//
    val userNote: String//
)
