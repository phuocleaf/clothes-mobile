package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.clothesapp.R
import com.example.clothesapp.databinding.ActivityDeliveryInformationBinding
import com.example.clothesapp.model.Cart
import com.example.clothesapp.model.CreateOrderData
import com.example.clothesapp.viewmodel.CreateOrderViewModel
import com.example.clothesapp.viewmodel.UserInfoViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityDeliveryInformationBinding
private lateinit var viewModel: UserInfoViewModel
private lateinit var createOrderViewModel: CreateOrderViewModel

class DeliveryInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        Paper.init(this)
        val user_id = Paper.book().read<String>("user_id")
        val total = intent.getIntExtra("total", 0)
        val cartList = Paper.book().read("cart", mutableListOf<Cart>().toList())
        user_id?.let { viewModel.getUserInfo(it) }

        viewModel.observeUserInfo().observe(
            this,
        ) {
            binding.etname.setText(it.name)
            binding.etphone.setText(it.phone)
            binding.etaddress.setText(it.address)
        }

        binding.tvtotal.text = "Tổng tiền: $total"

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        binding.btnOrder.setOnClickListener {
            val name = binding.etname.text.toString().trim()
            val phone = binding.etphone.text.toString().trim()
            val address = binding.etaddress.text.toString().trim()
            val note = binding.etnote.text.toString().trim()

            createOrderViewModel = ViewModelProvider(this)[CreateOrderViewModel::class.java]
            createOrderViewModel.createOrder(
                CreateOrderData(
                    userId = user_id!!,
                    userAddress = address,
                    userName = name,
                    userPhone = phone,
                    cartList = cartList!!,
                    total = total,
                    userNote = note
                )
            )

            createOrderViewModel.observeOrderDataResponse().observe(
                this,
            ) {
                if (it.isOrder) {
                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                    Paper.book().delete("cart")
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}