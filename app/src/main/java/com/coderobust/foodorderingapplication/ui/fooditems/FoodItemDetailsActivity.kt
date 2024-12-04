package com.coderobust.foodorderingapplication.ui.fooditems

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.ActivityFoodItemDetailsBinding
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodItemDetailsBinding
    private lateinit var foodItem: FoodItem
    val viewModel:FoodItemDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodItem=Gson().fromJson(intent.getStringExtra("data"),FoodItem::class.java)
        viewModel.checkInCart(foodItem)

        binding.tvFoodName.text = foodItem.name
        binding.tvFoodCategory.text = foodItem.category
        binding.tvFoodRating.text = foodItem.rating
        binding.tvFoodDescription.text = foodItem.description
        binding.tvFoodPrice.text = "${foodItem.price} Rs."

        var quantity = 1
        binding.tvQuantity.text = quantity.toString()

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnPlus.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnAddToCart.setOnClickListener {
            val cartItem=CartItem()
            cartItem.foodItem=foodItem
            cartItem.quantity=quantity

            viewModel.addToCart(cartItem)
        }

        lifecycleScope.launch {
            viewModel.doesExistInCart.collect{
                if (it==true) {
                    Toast.makeText(this@FoodItemDetailsActivity, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.cartItem.collect{
                if (it!=null) {
                    binding.btnAddToCart.visibility= View.GONE
                    binding.tvQuantity.text=it.quantity.toString()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                if (it!=null) {
                    Toast.makeText(this@FoodItemDetailsActivity, "Error ${it}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}