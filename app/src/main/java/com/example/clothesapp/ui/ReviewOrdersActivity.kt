package com.example.clothesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.clothesapp.R
import com.example.clothesapp.adapters.StatusFragmentAdapter
import com.example.clothesapp.databinding.ActivityReviewOrdersBinding
import com.google.android.material.tabs.TabLayout

private lateinit var binding: ActivityReviewOrdersBinding
private lateinit var adapter: StatusFragmentAdapter

class ReviewOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager

        adapter = StatusFragmentAdapter(supportFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận"))
        tabLayout.addTab(tabLayout.newTab().setText("Đang vận chuyển"))
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao"))
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}