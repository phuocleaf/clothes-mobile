package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clothesapp.R
import com.example.clothesapp.databinding.ActivitySignUpBinding
import com.example.clothesapp.viewmodel.SignUpViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySignUpBinding
private lateinit var viewModel: SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        Paper.init(this)
        if(Paper.book().read<String>("user_id") != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener{

            viewModel.signUp(
                binding.etname.text.toString(),
                binding.etemail.text.toString(),
                binding.etpassword.text.toString(),
                binding.etphone.text.toString(),
                binding.etaddress.text.toString())
            observeSignUpStatus()

        }

        binding.textButtonSignIn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun observeSignUpStatus(){
        val signUpObserver = Observer<Boolean> { isSignup ->
            if(isSignup){
                Toast.makeText(this,"Đăng kí thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.observeSignUpStatus().observe(this, signUpObserver)
    }
}