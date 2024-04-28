package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothesapp.databinding.ActivityClothesDetailBinding
import com.example.clothesapp.model.Cart
import com.example.clothesapp.utils.Utils
import io.paperdb.Paper


@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityClothesDetailBinding

class ClothesDetailActivity : AppCompatActivity() {

    var amount = 1
    lateinit var selectedSize: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)
        //Paper.book().delete("cart")

        binding = ActivityClothesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val _id = intent.getStringExtra("clothes_id")
        val image = intent.getStringExtra("clothes_image")
        val name = intent.getStringExtra("clothes_name")
        val price = intent.getIntExtra("clothes_price",0)
        val size_l = intent.getIntExtra("clothes_size_l",0)
        val size_m = intent.getIntExtra("clothes_size_m",0)
        val size_s = intent.getIntExtra("clothes_size_s",0)
        val size_xl = intent.getIntExtra("clothes_size_xl",0)

        val list = mutableListOf<String>()
        list.add("S")
        list.add("M")
        list.add("L")
        list.add("XL")

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.txtClothesName.text = name
        binding.txtClothesPrice.text = price.toString()
        val imageURL = Utils.BASE_URL + "image/" + image
        Glide.with(this).load(imageURL).into(binding.imgClothes)

        val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

        binding.spinner.adapter = sizeAdapter



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSize = list[position]
                amount = 1
                binding.txtamount.text = amount.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.txtClothesPrice.text = price.toString()
            }
        }

        binding.imgadd.setOnClickListener{
            val size = when (selectedSize) {
                "M" -> "size_m"
                "L" -> "size_l"
                "XL" -> "size_xl"
                "S" -> "size_s"
                else -> "size_s"
            }

            val isSizeAvailable = when (size) {
                "size_m" -> size_m - amount
                "size_l" -> size_l - amount
                "size_xl" -> size_xl - amount
                "size_s" -> size_s - amount
                else -> 0
            }

            if(amount < 5 && isSizeAvailable > 0) {
                amount++
                binding.txtamount.text = amount.toString()
            } else {
                showAlertDialog(this, amount, selectedSize)
            }
        }

        binding.imgsub.setOnClickListener{
            if(amount > 1){
                amount--
                binding.txtamount.text = amount.toString()
            }
        }

        binding.btnaddtocart.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            val cart = Cart(
                _id.toString(),
                image.toString(),
                name.toString(),
                price,
                size_l,
                size_m,
                size_s,
                size_xl,
                amount,
                selectedSize
            )

            var isExist = false

            var cartList = mutableListOf<Cart>()
            cartList = Paper.book().read("cart", mutableListOf())!!
            if(cartList.size > 0){
                for(i in 0 until cartList.size){
                    if(cartList[i]._id == cart._id && cartList[i].Size == cart.Size){
                        isExist = true
                        cartList[i].quantity += cart.quantity
                        Paper.book().write("cart",cartList)
                    }
                }
                if(!isExist){
                    cartList.add(cart)
                    Paper.book().write("cart",cartList)
                }
            } else{
                cartList.add(cart)
                Paper.book().write("cart",cartList)
            }
            Toast.makeText(this,"Đã thêm vào giỏ",Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
    }
    private fun showAlertDialog(context: Context, amount: Int, size: String) {
        // Tạo đối tượng AlertDialog.Builder
        val builder = AlertDialog.Builder(context)

        // Thiết lập các thuộc tính của hộp thoại
        builder.setTitle("Cảnh báo")
            .setMessage("Bạn chỉ có thể thêm tối đa ${amount} sản phẩm size "+ size+ " vào giỏ hàng")
            .setPositiveButton("OK") { dialog, _ ->
                // Đóng hộp thoại khi người dùng nhấn nút "OK"
                dialog.dismiss()
            }

        // Hiển thị hộp thoại
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}