package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.UserInfo
import com.example.clothesapp.retrofit.RetrofitClient

class UserInfoViewModel(): ViewModel() {
    private var userInfo = MutableLiveData<UserInfo>()

    fun getUserInfo(id: String) {
        // get user info from server
        RetrofitClient.api.getUser(id).enqueue(object : retrofit2.Callback<UserInfo> {
            override fun onResponse(call: retrofit2.Call<UserInfo>, response: retrofit2.Response<UserInfo>) {
                response.body()?.let {
                    userInfo.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<UserInfo>, t: Throwable) {
                userInfo.value = UserInfo(
                    _id = "",
                    address = "",
                    email = "",
                    name = "",
                    password = "",
                    phone = ""
                )
            }
        })
    }

    fun observeUserInfo(): LiveData<UserInfo> {
        return userInfo
    }
}