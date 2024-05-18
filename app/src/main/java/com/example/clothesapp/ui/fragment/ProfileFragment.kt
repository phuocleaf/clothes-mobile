package com.example.clothesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.clothesapp.R
import com.example.clothesapp.databinding.FragmentProfileBinding
import com.example.clothesapp.ui.MainActivity
import com.example.clothesapp.ui.ReviewOrdersActivity
import com.example.clothesapp.ui.SignInActivity
import com.example.clothesapp.ui.SignUpActivity
import com.example.clothesapp.ui.UserInfoActivity
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

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        val userName = Paper.book().read("user_name", "")
        binding.tvUserName.text = userName.toString()

        binding.btnLogout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("Xác nhận")
                setMessage("Bạn có chắc chắn muốn đăng xuất?")
                setPositiveButton("Đồng ý") { dialog, _ ->
                    // Xóa thông tin user_id và giỏ hàng từ Paper
                    Paper.book().delete("user_id")
                    Paper.book().delete("cart")
                    // Xóa tên người dùng trên giao diện
                    binding.tvUserName.text = ""
                    // Chuyển đến màn hình đăng nhập
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss() // Đóng dialog sau khi xác nhận đăng xuất
                }
                setNegativeButton("Hủy") { dialog, _ ->
                    dialog.dismiss() // Đóng dialog nếu người dùng không muốn đăng xuất
                }
            }

            // Tạo và hiển thị AlertDialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


        binding.llOrderProcessing.setOnClickListener {
            val intent = Intent(requireContext(), ReviewOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.lluserinfomation.setOnClickListener {
            val intent = Intent(requireContext(), UserInfoActivity::class.java)
            startActivity(intent)
        }
    }
}