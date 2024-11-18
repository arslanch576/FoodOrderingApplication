package com.coderobust.foodorderingapplication.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.MainActivity
import com.coderobust.foodorderingapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding:ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= LoginViewModel()

        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Please wait while we check your credentials...")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.currentUser.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginbtn.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            val password=binding.password.editText?.text.toString()

            if(!email.trim().contains("@")){
                Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password.trim().length<6){
                Toast.makeText(this,"Invalid Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressDialog.show()
            viewModel.login(email,password)

        }


    }
}