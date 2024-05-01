package com.example.clothesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothesapp.databinding.ItemClotherReviewBinding
import com.example.clothesapp.model.ReviewOrderClothesItem
import com.example.clothesapp.utils.Utils

class OrderClothesAdapter(): RecyclerView.Adapter<OrderClothesAdapter.OrderClothesViewHolder>() {

    private var detailList = ArrayList<ReviewOrderClothesItem>()

    inner class OrderClothesViewHolder(var binding: ItemClotherReviewBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderClothesAdapter.OrderClothesViewHolder {
        return OrderClothesViewHolder(ItemClotherReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: OrderClothesAdapter.OrderClothesViewHolder,
        position: Int
    ) {
        holder.binding.txtClothesName.text = detailList[position].name
        holder.binding.txtClothesPrice.text = "Giá: " + detailList[position].price.toString()
        holder.binding.txtquantity.text = "Số lượng: "+ detailList[position].quantity.toString()
        holder.binding.txtClothesSize.text = "Size: "+ detailList[position].Size

        val imageURL = Utils.BASE_URL + "image/" + detailList[position].image
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgClothes)
    }

    override fun getItemCount(): Int {
        return detailList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setDataDetail(details: ArrayList<ReviewOrderClothesItem>){
        this.detailList = details
        notifyDataSetChanged()
    }
}