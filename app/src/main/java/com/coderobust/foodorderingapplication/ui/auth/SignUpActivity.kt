package com.coderobust.foodorderingapplication.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.ui.main.MainActivity
import com.coderobust.foodorderingapplication.databinding.ActivitySignupBinding
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding: ActivitySignupBinding
    lateinit var viewModel: LoginSignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginSignupViewModel(AuthRepository())

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while we create your account...")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.currentUser.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    Toast.makeText(this@SignUpActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.signupTxt.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
        binding.loginbtn.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            progressDialog.show()
            viewModel.signUp(
                email,
                password,
                binding.confirmPassword.editText?.text.toString(),
                binding.name.editText?.text.toString()
            )

        }


    }
}