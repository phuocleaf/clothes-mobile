package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clothesapp.R
import com.example.clothesapp.databinding.ActivityUpdateUserInfoBinding
import com.example.clothesapp.model.UserInfo
import com.example.clothesapp.viewmodel.UpdateUserInfoViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityUpdateUserInfoBinding
private lateinit var viewModel: UpdateUserInfoViewModel

class UpdateUserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UpdateUserInfoViewModel::class.java]

        Paper.init(this)
        val userId = Paper.book().read<String>("user_id").toString()

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        val userName = intent.getStringExtra("user_name")
        val userAddress = intent.getStringExtra("user_address")
        val userPhone = intent.getStringExtra("user_phone")

        binding.etname.setText(userName)
        binding.etaddress.setText(userAddress)
        binding.etphone.setText(userPhone)

        binding.btnUpdate.setOnClickListener {
            viewModel.updateUserInfo(
                userId,
                UserInfo(
                    _id = userId,
                    address = binding.etaddress.text.toString().trim(),
                    email = "",
                    name = binding.etname.text.toString().trim(),
                    password = "",
                    phone = binding.etphone.text.toString().trim()
                )
            )
            observeUpdateUserInfoStatus()
        }
    }

    private fun observeUpdateUserInfoStatus() {
        val updateUserInfoObserver = Observer<Boolean> { isUpdateUserInfo ->
            if (isUpdateUserInfo) {
                Paper.book().write("user_name", binding.etname.text.toString().trim())
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserInfoActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Handle error
            }
        }
        viewModel.observeUpdateUserInfoStatus().observe(this, updateUserInfoObserver)
    }
}