package com.coderobust.foodorderingapplication.ui.auth

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.databinding.ActivityResetPasswordBinding
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding:ActivityResetPasswordBinding
    val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Please wait a while...")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.isEmailSent.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    val builder=MaterialAlertDialogBuilder(this@ResetPasswordActivity)
                    builder.setMessage("Password reset link sent to your email, check your inbox and click on link to reset your password")
                    builder.setCancelable(false)
                    builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        finish()
                    })
                    builder.show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    Toast.makeText(this@ResetPasswordActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signupTxt.setOnClickListener {
            finish()
        }

        binding.loginbtn.setOnClickListener {
            val email=binding.email.editText?.text.toString()

            progressDialog.show()
            viewModel.resetPassword(email)

        }


    }
}