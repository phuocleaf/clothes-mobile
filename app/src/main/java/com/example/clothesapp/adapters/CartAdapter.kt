package com.example.clothesapp.adapters

import android.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothesapp.databinding.ItemCartBinding
import com.example.clothesapp.model.Cart
import com.example.clothesapp.onclick.ChangeNumListener
import com.example.clothesapp.onclick.ChangeSizeInterface

import com.example.clothesapp.utils.Utils

class CartAdapter(private val changeNumListener: ChangeNumListener,
                  private val changeSizeInterface: ChangeSizeInterface
): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private var cartList = ArrayList<Cart>()

    class MyViewHolder(var binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.txtClothesName.text = cartList[position].name
        holder.binding.txtClothesPrice.text = cartList[position].price.toString()
        holder.binding.txtquantity.text = cartList[position].quantity.toString()
        val imageURL = Utils.BASE_URL + "image/" + cartList[position].image
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgClothes)

        val list = mutableListOf<String>()
        list.add("S")
        list.add("M")
        list.add("L")
        list.add("XL")


        val sizeAdapter = ArrayAdapter(holder.itemView.context, R.layout.simple_spinner_item, list)

        holder.binding.spinnerClothesSize.adapter = sizeAdapter
        val cartListSize = cartList[position].Size
        val cartListPosition = position
        holder.binding.spinnerClothesSize.setSelection(list.indexOf(cartListSize))

        holder.binding.spinnerClothesSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSize = list[position]

                if( cartListSize != selectedSize) { // Nếu kích thước đã chọn trùng với kích thước hiện tại, không cần thực hiện gì cả
                    changeSizeInterface.changeSize(cartListPosition, selectedSize)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                cartList[position].Size = "S" // Thiết lập kích thước mặc định nếu không có kích thước nào được chọn
            }
        }


        holder.binding.imgadd.setOnClickListener {
            changeNumListener.changeNum(position,true)
        }
        holder.binding.imgsub.setOnClickListener {
            changeNumListener.changeNum(position,false)
        }


    }

    fun getCartAt(position: Int): Cart {
        return cartList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataCart(carts: ArrayList<Cart>){
        this.cartList = carts
        notifyDataSetChanged()
    }
}