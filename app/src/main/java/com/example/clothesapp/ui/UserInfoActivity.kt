package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.clothesapp.R
import com.example.clothesapp.databinding.ActivityUserInfoBinding
import com.example.clothesapp.viewmodel.UserInfoViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityUserInfoBinding
private lateinit var viewModel: UserInfoViewModel

class UserInfoActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        Paper.init(this)
        val user_id = Paper.book().read<String>("user_id")

        user_id?.let { viewModel.getUserInfo(it) }

        var user_name = ""
        var user_email = ""
        var user_address = ""
        var user_phone = ""

        viewModel.observeUserInfo().observe(
            this,
        ) {
            user_name = it.name
            user_email = it.email
            user_address = it.address
            user_phone = it.phone
            binding.tvname.text = "Tên: "+ it.name
            binding.tvemail.text = "Email: "+ it.email
            binding.tvaddress.text = "Địa chỉ: "+ it.address
            binding.tvphone.text = "Số điện thoại: "+ it.phone
        }

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        binding.tvUpdateInfo.setOnClickListener {
            val intent = Intent(this, UpdateUserInfoActivity::class.java)
            intent.putExtra("user_name", user_name)
            intent.putExtra("user_address", user_address)
            intent.putExtra("user_phone", user_phone)
            startActivity(intent)
            finish()
        }
    }
}