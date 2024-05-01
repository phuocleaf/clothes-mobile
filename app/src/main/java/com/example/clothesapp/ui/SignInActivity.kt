package com.example.clothesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clothesapp.R
import com.example.clothesapp.databinding.ActivitySignInBinding
import com.example.clothesapp.model.SignInResponse
import com.example.clothesapp.viewmodel.SignInViewModel
import io.paperdb.Paper
import kotlinx.coroutines.delay

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySignInBinding
private lateinit var viewModel: SignInViewModel
class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        Paper.init(this)
        if(Paper.book().read<String>("user_id") != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnsignin.setOnClickListener{
            viewModel.signIn(binding.etemail.text.toString().trim(),binding.etpassword.text.toString().trim())
            observeSignInStatus()
        }

        binding.textButtonSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun observeSignInStatus(){
        val signInObserver = Observer<SignInResponse> { signInResponse ->
            if(signInResponse.isSignIn){
                Paper.init(this)
                Paper.book().write("user_id", signInResponse._id)
                Paper.book().write("user_name", signInResponse.name)
                Toast.makeText(this,"Đăng nhập thành công", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "có lỗi xảy ra", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.observerSignInResponseData().observe(this, signInObserver)
    }
}