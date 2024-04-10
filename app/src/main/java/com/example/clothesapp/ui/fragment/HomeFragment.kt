package com.example.clothesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothesapp.adapters.ClothesAdapter
import com.example.clothesapp.databinding.FragmentHomeBinding
import com.example.clothesapp.model.ClothesItem
import com.example.clothesapp.onclick.OnClickInterface
import com.example.clothesapp.ui.ClothesDetailActivity
import com.example.clothesapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var clothesAdapter: ClothesAdapter
    //private var clothesList = ArrayList<ClothesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.getClothes()

        observerLiveData()


    }


    private fun initView() {
        clothesAdapter = ClothesAdapter(object: OnClickInterface {
            override fun onClick(position: Int) {
                val clothes = clothesAdapter.getClothesItemAt(position)
                val _id = clothes._id
                val image = clothes.image
                val name = clothes.name
                val price = clothes.price
                val size_l = clothes.size_l
                val size_m = clothes.size_m
                val size_s = clothes.size_s
                val size_xl = clothes.size_xl

                val intent = Intent(context, ClothesDetailActivity::class.java)
                intent.putExtra("clothes_id", _id)
                intent.putExtra("clothes_image", image)
                intent.putExtra("clothes_name", name)
                intent.putExtra("clothes_price", price)
                intent.putExtra("clothes_size_l", size_l)
                intent.putExtra("clothes_size_m", size_m)
                intent.putExtra("clothes_size_s", size_s)
                intent.putExtra("clothes_size_xl", size_xl)

                startActivity(intent)
            }
        })
        binding.recyclerClothes.apply {
            adapter = clothesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observerLiveData() {
        viewModel.observerClothesLiveData().observe(viewLifecycleOwner) { clothes ->
            clothesAdapter.setDataClothes(clothes as ArrayList<ClothesItem>)
        }
    }
}