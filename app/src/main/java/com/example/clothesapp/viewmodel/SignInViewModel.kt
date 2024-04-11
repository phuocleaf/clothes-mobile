package com.example.clothesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.SignInResponse
import com.example.clothesapp.model.UserSignInData
import com.example.clothesapp.retrofit.RetrofitClient

class SignInViewModel(): ViewModel() {

    private var _id: String = ""
    private var isSignIn: Boolean = false
    private var name: String = ""
    private var message = ""

    fun signIn(email: String, password: String) {
        val userSignInData = UserSignInData(email, password)

        RetrofitClient.api.signIn(userSignInData).enqueue(object : retrofit2.Callback<SignInResponse> {
            override fun onResponse(call: retrofit2.Call<SignInResponse>, response: retrofit2.Response<SignInResponse>) {
                response.body()?.let { signInResponse ->
                    isSignIn = signInResponse.isSignIn
                    _id = signInResponse._id
                    name = signInResponse.name
                    message = signInResponse.message
                }
            }

            override fun onFailure(call: retrofit2.Call<SignInResponse>, t: Throwable) {
                isSignIn = false
            }
        })
    }

    fun observerSignInResponseData(): SignInResponse {
        return SignInResponse(
            isSignIn = isSignIn,
            _id = _id,
            name = name,
            message = message
        )
    }
}