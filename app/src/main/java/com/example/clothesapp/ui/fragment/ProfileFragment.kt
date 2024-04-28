package com.example.clothesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clothesapp.R
import com.example.clothesapp.databinding.FragmentProfileBinding
import com.example.clothesapp.ui.MainActivity
import com.example.clothesapp.ui.ReviewOrdersActivity
import com.example.clothesapp.ui.SignUpActivity
import io.paperdb.Paper

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        val userName = Paper.book().read("user_name", "")
        binding.tvUserName.text = userName.toString()

        binding.btnLogout.setOnClickListener {
            Paper.book().delete("user_id")
            binding.tvUserName.text = ""
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.llOrderProcessing.setOnClickListener {
            val intent = Intent(requireContext(), ReviewOrdersActivity::class.java)
            startActivity(intent)
        }
    }
}