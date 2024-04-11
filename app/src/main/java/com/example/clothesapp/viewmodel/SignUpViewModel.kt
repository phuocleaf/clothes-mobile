package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.SignUpResponse
import com.example.clothesapp.model.UserSignUpData
import com.example.clothesapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class SignUpViewModel(): ViewModel() {

    private var isSignUp = MutableLiveData<Boolean>()

    fun signUp(name: String, email: String, password: String, phone: String, address: String) {
        val userSignUpData = UserSignUpData(name, email, password, phone, address)
        RetrofitClient.api.signUp(userSignUpData).enqueue(object : retrofit2.Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                response.body()?.let { signUpResponse ->
                    isSignUp.value = signUpResponse.isSignUp
                }
            }

            override fun onFailure(call: retrofit2.Call<SignUpResponse>, t: Throwable) {
                isSignUp.value = false
            }
        })
    }

    fun observeSignUpStatus(): LiveData<Boolean> {
        return isSignUp
    }

}