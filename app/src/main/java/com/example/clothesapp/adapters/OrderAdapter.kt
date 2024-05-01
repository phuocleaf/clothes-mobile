package com.example.clothesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.databinding.ItemOrderReviewBinding
import com.example.clothesapp.model.ReviewOrder
import com.example.clothesapp.model.ReviewOrderItem
import com.example.clothesapp.onclick.OnClickInterface


class OrderAdapter(private val onClick: OnClickInterface): RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    private var orderList = ArrayList<ReviewOrderItem>()

    inner class MyViewHolder( var binding: ItemOrderReviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemOrderReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvorderid.text = orderList[position]._id.toString()
        val dateTime: String = orderList[position].created_at
        holder.binding.tvordertime.text = dateTime
        holder.binding.tvordertotal.text = orderList[position].total.toString()

        holder.itemView.setOnClickListener {
            onClick.onClick(position)
        }
    }

    fun getOrderAt(position: Int): ReviewOrderItem {
        return orderList[position]
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setDataOrder(orders: ArrayList<ReviewOrderItem>){
        this.orderList = orders
        notifyDataSetChanged()
    }
}