package com.coderobust.foodorderingapplication.ui.fooditems

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.ActivityAddFoodItemsBinding
import com.coderobust.foodorderingapplication.databinding.ActivityMainBinding
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class AddFoodItemsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFoodItemsBinding;
    val viewModel: AddFoodItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.isSaved.collect {
                if (it == true) {
                    Toast.makeText(
                        this@AddFoodItemsActivity,
                        "Food item saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    Log.d("testing", "onCreate: $it")
                    Toast.makeText(this@AddFoodItemsActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonSaveFood.setOnClickListener {
            val name = binding.editTextFoodName.text.toString().trim()
            val description = binding.editTextFoodDescription.text.toString().trim()
            val price = binding.editTextFoodPrice.text.toString().trim()
            val category = binding.editTextFoodCategory.text.toString().trim()

            // Validation
            if (name.isEmpty() || description.isEmpty() || price.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val priceValue = price.toInt()

            // Create the food item object (replace with your logic to save the data)
            val foodItem = FoodItem()
            foodItem.name = name
            foodItem.description = description
            foodItem.price = priceValue
            foodItem.category = category

            viewModel.saveFoodItem(foodItem)

        }

    }
}