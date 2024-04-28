package com.example.clothesapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clothesapp.ui.fragment.ChoXacNhanFragment
import com.example.clothesapp.ui.fragment.DaGiaoFragment
import com.example.clothesapp.ui.fragment.DaHuyFragment
import com.example.clothesapp.ui.fragment.DangVanChuyenFragment

class StatusFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle
) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChoXacNhanFragment()
            }
            1 -> {
                DangVanChuyenFragment()
            }
            2 -> {
                DaGiaoFragment()
            }
            3 -> {
                DaHuyFragment()
            }
            else -> {
                ChoXacNhanFragment()
            }
        }
    }
}