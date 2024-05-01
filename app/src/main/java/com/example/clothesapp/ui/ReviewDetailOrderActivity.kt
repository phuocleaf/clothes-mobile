package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothesapp.R
import com.example.clothesapp.adapters.OrderClothesAdapter
import com.example.clothesapp.databinding.ActivityClothesDetailBinding
import com.example.clothesapp.databinding.ActivityReviewDetailOrderBinding
import com.example.clothesapp.model.ReviewOrderClothesItem
import com.example.clothesapp.model.UpdateOrderStatusResponse
import com.example.clothesapp.viewmodel.CancelOrderViewModel
import com.example.clothesapp.viewmodel.OrderClothesViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityReviewDetailOrderBinding
private lateinit var viewModel: OrderClothesViewModel
private lateinit var clothesAdapter: OrderClothesAdapter
private lateinit var cancelOrderViewModel: CancelOrderViewModel
private var clothesList = ArrayList<ReviewOrderClothesItem>()

class ReviewDetailOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OrderClothesViewModel::class.java]
        cancelOrderViewModel = ViewModelProvider(this)[CancelOrderViewModel::class.java]

        Paper.init(this)
        val orderId = intent.getStringExtra("order_id").toString()
        val status = intent.getStringExtra("order_status").toString()
        if (status == "Chờ xác nhận") {
            binding.btnCancel.visibility = View.VISIBLE
        }

        viewModel.getOrderClothes(orderId)
        initView()
    }

    private fun observerLiveData() {
        viewModel.observerReviewOrderClothesLiveData().observe(this) {
            clothesList = it as ArrayList<ReviewOrderClothesItem>
            recyclerViewClothes()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        val userName = intent.getStringExtra("order_user_name")
        val userAddress = intent.getStringExtra("order_user_address")
        val userPhone = intent.getStringExtra("order_user_phone")
        val time = intent.getStringExtra("order_time")
        val note = intent.getStringExtra("order_note")
        val total = intent.getIntExtra("order_total", 0)

        binding.imageViewBack.setOnClickListener {
            val status = intent.getStringExtra("order_status").toString()
            if (status == "Chờ xác nhận") {
                val intent = Intent(this, ReviewOrdersActivity::class.java)
                startActivity(intent)
            }
            finish()
        }

        binding.textViewName.text = "Tên khách hàng: $userName"
        binding.textViewAddress.text = "Địa chỉ: $userAddress"
        binding.textViewPhoneNumber.text = "Số điện thoại: $userPhone"
        binding.textViewTime.text = "Thời gian: $time"
        binding.textViewNote.text = "Ghi chú: $note"
        binding.textViewTotalPrice.text = "Tổng tiền: $total"

        observerLiveData()

        binding.btnCancel.setOnClickListener {
            val orderId = intent.getStringExtra("order_id").toString()

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle("Xác nhận")
                setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?")
                setPositiveButton("Đồng ý") { dialog, _ ->
                    // Gọi hàm để hủy đơn hàng ở đây
                    cancelOrderViewModel.cancelOrder(orderId)
                    // Theo dõi phản hồi từ việc hủy đơn hàng
                    observerCancelResponse()
                    dialog.dismiss() // Đóng dialog sau khi người dùng xác nhận
                }
                setNegativeButton("Hủy") { dialog, _ ->
                    dialog.dismiss() // Đóng dialog nếu người dùng không muốn hủy đơn hàng
                }
            }

            // Tạo và hiển thị AlertDialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

    private fun observerCancelResponse() {
        val cancelOrderObserver = Observer<UpdateOrderStatusResponse> { updateOrderStatusResponse ->
            if (updateOrderStatusResponse.isOrder) {
                Toast.makeText(this, "Huỷ đơn thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ReviewOrdersActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
            }
        }
        cancelOrderViewModel.observeUpdateOrderStatusResponse().observe(this, cancelOrderObserver)
    }

    private fun recyclerViewClothes(){
        clothesAdapter = OrderClothesAdapter()
        clothesAdapter.setDataDetail(clothesList)
        binding.recyclerViewClothes.apply {
            adapter = clothesAdapter
            layoutManager = LinearLayoutManager(this@ReviewDetailOrderActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

}