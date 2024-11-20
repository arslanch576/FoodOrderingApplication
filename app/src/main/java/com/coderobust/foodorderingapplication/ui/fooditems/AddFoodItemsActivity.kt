package com.coderobust.foodorderingapplication.ui.fooditems

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import kotlinx.coroutines.launch
import kotlin.math.log

class AddFoodItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_food_items)

        val viewModel=AddFoodItemViewModel(FoodItemRepository())

        lifecycleScope.launch {
            viewModel.isSaved.collect {
                if (it==true){
                    Toast.makeText(this@AddFoodItemsActivity, "Food item saved successfully", Toast.LENGTH_SHORT).show()
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

        val foodItem=FoodItem()
        foodItem.name="Nomnosh special pizza"
        foodItem.description="Pizza with extra cheese"
        foodItem.price=1200;
        foodItem.category="Pizza"

        viewModel.saveFoodItem(foodItem)
    }
}