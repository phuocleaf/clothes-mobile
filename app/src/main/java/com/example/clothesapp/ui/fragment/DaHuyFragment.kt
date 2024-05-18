package com.example.clothesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothesapp.R
import com.example.clothesapp.adapters.OrderAdapter
import com.example.clothesapp.databinding.FragmentChoXacNhanBinding
import com.example.clothesapp.databinding.FragmentDaHuyBinding
import com.example.clothesapp.model.ReviewOrderItem
import com.example.clothesapp.onclick.OnClickInterface
import com.example.clothesapp.ui.ReviewDetailOrderActivity
import com.example.clothesapp.viewmodel.ReviewOrderViewModel
import io.paperdb.Paper


class DaHuyFragment : Fragment() {
    private lateinit var binding: FragmentDaHuyBinding
    private lateinit var reviewOrderViewModel: ReviewOrderViewModel
    private lateinit var orderAdapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewOrderViewModel = ViewModelProvider(this)[ReviewOrderViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaHuyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        Paper.init(requireContext())
        val userId = Paper.book().read("user_id", "").toString()
        reviewOrderViewModel.getReviewOrder(userId,"Đã huỷ")
        observerLiveData()
    }

    override fun onResume() {
        super.onResume()
        Paper.init(requireContext())
        val userId = Paper.book().read("user_id", "").toString()
        reviewOrderViewModel.getReviewOrder(userId,"Đã huỷ")
        observerLiveData()
    }

    private fun initView() {
        orderAdapter = OrderAdapter(object: OnClickInterface{
            override fun onClick(position: Int) {
                val order = orderAdapter.getOrderAt(position)
                val intent = Intent(requireContext(), ReviewDetailOrderActivity::class.java)
                intent.putExtra("order_id", order._id)
                intent.putExtra("order_user_name", order.userName)
                intent.putExtra("order_user_address", order.userAddress)
                intent.putExtra("order_user_phone", order.userPhone)
                intent.putExtra("order_time", order.created_at)
                intent.putExtra("order_note", order.userNote)
                intent.putExtra("order_total", order.total)
                startActivity(intent)
                //requireActivity().finish()
            }
        })
        binding.recyclerOrder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }

    private fun observerLiveData() {
        reviewOrderViewModel.observerReviewOrderLiveData().observe(viewLifecycleOwner) {
            orderAdapter.setDataOrder(it as ArrayList<ReviewOrderItem>)
        }
    }
}