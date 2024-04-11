package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.SignInResponse
import com.example.clothesapp.model.UserSignInData
import com.example.clothesapp.retrofit.RetrofitClient

class SignInViewModel(): ViewModel() {

    private var signInResponse = MutableLiveData<SignInResponse>()

    fun signIn(email: String, password: String) {
        val userSignInData = UserSignInData(email, password)

        RetrofitClient.api.signIn(userSignInData).enqueue(object : retrofit2.Callback<SignInResponse> {
            override fun onResponse(call: retrofit2.Call<SignInResponse>, response: retrofit2.Response<SignInResponse>) {
                response.body()?.let {
                    signInResponse.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<SignInResponse>, t: Throwable) {
                signInResponse.value = SignInResponse(
                    isSignIn = false,
                    _id = "",
                    name = "",
                    message = t.message.toString()
                )
            }
        })
    }

    fun observerSignInResponseData(): LiveData<SignInResponse> {
        return signInResponse
    }
}