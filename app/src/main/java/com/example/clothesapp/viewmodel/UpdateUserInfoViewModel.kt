package com.example.clothesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesapp.model.UpdateUserInfoResponse
import com.example.clothesapp.model.UserInfo
import com.example.clothesapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class UpdateUserInfoViewModel(): ViewModel() {
    private var isUpdateUserInfo = MutableLiveData<Boolean>()

    fun updateUserInfo(id: String, userData: UserInfo) {
        RetrofitClient.api.updateUserInfo(id, userData).enqueue(object : retrofit2.Callback<UpdateUserInfoResponse> {
            override fun onResponse(
                call: Call<UpdateUserInfoResponse>,
                response: Response<UpdateUserInfoResponse>
            ) {
                response.body()?.let { updateUserInfoResponse ->
                    isUpdateUserInfo.value = updateUserInfoResponse.isUpdate
                }
            }

            override fun onFailure(call: retrofit2.Call<UpdateUserInfoResponse>, t: Throwable) {
                isUpdateUserInfo.value = false
            }
        })
    }

    fun observeUpdateUserInfoStatus(): LiveData<Boolean> {
        return isUpdateUserInfo
    }
}