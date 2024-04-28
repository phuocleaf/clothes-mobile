package com.example.clothesapp.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothesapp.adapters.CartAdapter
import com.example.clothesapp.databinding.FragmentCartBinding
import com.example.clothesapp.model.Cart
import com.example.clothesapp.onclick.ChangeNumListener
import com.example.clothesapp.onclick.ChangeSizeInterface
import com.example.clothesapp.ui.DeliveryInformationActivity
import io.paperdb.Paper
import java.util.Objects

class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var cartList: MutableList<Cart>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        cartList = Paper.book().read("cart", mutableListOf())!!
        var total = 0
        for (i in cartList){
            total += i.price * i.quantity
        }
        binding.txtmoney.text = total.toString()
        total = 0
        cartAdapter = CartAdapter(object : ChangeNumListener {
            override fun changeNum(position: Int, isAdd: Boolean) {
                if (isAdd){
                    if (cartList[position].quantity >= 5){
                        cartList[position].quantity = 5
                        Toast.makeText(context, "Bạn đã thêm số lượng tối đa của mặt hàng này", Toast.LENGTH_SHORT).show()
                    } else {
                        cartList[position].quantity++
                    }
                } else {
                    if (cartList[position].quantity > 1) {
                        cartList[position].quantity--
                    } else {
                        val alertDialogBuilder = AlertDialog.Builder(context)
                        alertDialogBuilder.setTitle("Xoá sản phẩm")
                        alertDialogBuilder.setMessage("Bạn có muốn xoá sản phẩm này khỏi giỏ hàng không?")
                        alertDialogBuilder.setPositiveButton("Xoá") { dialog, which ->
                            cartList.removeAt(position)
                            Paper.book().write("cart",cartList)
                            cartAdapter.setDataCart(cartList as ArrayList<Cart>)

                            for (i in cartList){
                                total += i.price * i.quantity
                            }
                            binding.txtmoney.text = total.toString()
                            total = 0
                            Toast.makeText(context, "Đã xoá sản phẩm", Toast.LENGTH_SHORT).show()
                        }
                        alertDialogBuilder.setNegativeButton("Hủy") { dialog, which ->

                        }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                    }

                }
                Paper.book().write("cart",cartList)
                cartAdapter.setDataCart(cartList as ArrayList<Cart>)

                for (i in cartList){
                    total += i.price * i.quantity
                }
                binding.txtmoney.text = total.toString()
                total = 0
            }

        }, object : ChangeSizeInterface {
            override fun changeSize(position: Int, size: String) {
                var isExist = false
                for (item in cartList) {
                    if (item._id == cartList[position]._id && item.Size == size) {
                        isExist = true
                        item.quantity += cartList[position].quantity
                        cartList.removeAt(position)
                        break
                    }
                }
                if (!isExist) {
                    cartList[position].Size = size
                }
                Paper.book().write("cart", cartList)
                cartAdapter.setDataCart(cartList as ArrayList<Cart>)
            }

        })
        cartAdapter.setDataCart(cartList as ArrayList<Cart>)
        binding.rvCart.adapter = cartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,false
        )

        binding.btnbuy.setOnClickListener {
            if (cartList.isEmpty()){
                Toast.makeText(context, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, DeliveryInformationActivity::class.java)
                intent.putExtra("total", binding.txtmoney.text.toString().toInt())
                startActivity(intent)
            }
        }

    }
}