package com.coderobust.foodorderingapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderobust.foodorderingapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
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

            val progressDialog=ProgressDialog(this)
            progressDialog.setMessage("Please wait while we check your credentials...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener {
                progressDialog.dismiss()
                if (it.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}