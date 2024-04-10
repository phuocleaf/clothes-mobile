package com.example.clothesapp.model

data class SignInResponse(
    val message: String,
    val _id: String,
    val isSignIn: Boolean,
    val name: String,
)
