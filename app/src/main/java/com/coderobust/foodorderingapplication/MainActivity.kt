package com.coderobust.foodorderingapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.MainViewModel
import com.coderobust.foodorderingapplication.ui.fooditems.AddFoodItemsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, AddFoodItemsActivity::class.java))
        }

        val viewModel= MainViewModel(FoodItemRepository())

        lifecycleScope.launch {
            viewModel.foodItems.collect{
                Toast.makeText(this@MainActivity, "Food items loaded (${it.size})", Toast.LENGTH_SHORT).show()
            }
        }


    }
}