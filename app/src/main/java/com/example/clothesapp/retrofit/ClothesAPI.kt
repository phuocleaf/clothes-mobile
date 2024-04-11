package com.example.clothesapp.retrofit

import com.example.clothesapp.model.Clothes
import com.example.clothesapp.model.SignInResponse
import com.example.clothesapp.model.SignUpResponse
import com.example.clothesapp.model.UserSignInData
import com.example.clothesapp.model.UserSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClothesAPI {

    @GET("products/get-products")
    fun getClothes():Call<Clothes>

    @POST("users/sign-in")
    fun signIn(
        @Body userSignInData: UserSignInData
    ):Call<SignInResponse>

    @POST("users/sign-up")
    fun signUp(
        @Body userSignupData: UserSignUpData
    ):Call<SignUpResponse>
}