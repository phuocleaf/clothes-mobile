package com.example.clothesapp.retrofit

import com.example.clothesapp.model.Clothes
import com.example.clothesapp.model.CreateOrderData
import com.example.clothesapp.model.CreateOrderResponse
import com.example.clothesapp.model.SignInResponse
import com.example.clothesapp.model.SignUpResponse
import com.example.clothesapp.model.UserInfo
import com.example.clothesapp.model.UserSignInData
import com.example.clothesapp.model.UserSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("users/get-user/{id}")
    fun getUser(
        @Path("id") id:String
    ):Call<UserInfo>

    @POST("orders/create-order")
    fun createOrder(
        @Body orderData: CreateOrderData
    ):Call<CreateOrderResponse>

}