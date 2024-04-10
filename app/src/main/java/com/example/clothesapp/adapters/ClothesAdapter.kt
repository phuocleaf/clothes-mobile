package com.example.clothesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothesapp.databinding.FragmentHomeBinding
import com.example.clothesapp.databinding.ItemClothesBinding
import com.example.clothesapp.model.ClothesItem
import com.example.clothesapp.onclick.OnClickInterface
import com.example.clothesapp.utils.Utils

class ClothesAdapter(private val onClick: OnClickInterface):RecyclerView.Adapter<ClothesAdapter.MyViewHolder>() {
    private var clothesList = ArrayList<ClothesItem>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemClothesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return clothesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtClothesName.text = clothesList[position].name
        holder.binding.txtClothesPrice.text = clothesList[position].price.toString()
        val imageURL = Utils.BASE_URL + "image/" + clothesList[position].image
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgClothes)

        holder.itemView.setOnClickListener {
            onClick.onClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataClothes(foods: ArrayList<ClothesItem>){
        this.clothesList = foods
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemClothesBinding):RecyclerView.ViewHolder(binding.root) {

    }

    fun getClothesItemAt(position: Int): ClothesItem {
        return clothesList[position]
    }

    fun getClothesList(): ArrayList<ClothesItem>{
        return clothesList
    }
}