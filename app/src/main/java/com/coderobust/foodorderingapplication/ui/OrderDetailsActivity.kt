package com.coderobust.foodorderingapplication.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.ActivityMainBinding
import com.coderobust.foodorderingapplication.databinding.ActivityOrderDetailsBinding
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.google.gson.Gson

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding;
    lateinit var order:Order
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order= Gson().fromJson(intent.getStringExtra("data"),Order::class.java)

        binding.status.text = order.status
        binding.textFoodName.text = order.customerName
        binding.textFoodPrice.text = "${order.total} Rs."
        binding.textFoodItems.text = order.orderItems.map { "${it.quantity} x ${it.name}" }.joinToString("\n")

        var isAdmin=false
        if (AuthRepository().getCurrentUser()?.email.equals("arslanch576@gmail.com"))
            isAdmin=true

        if (!order.status.equals("Delivered")){
            binding.submit.visibility= View.GONE
            binding.review.visibility= View.GONE
        }
        if (!order.status.equals("Order Confirmed")||!isAdmin){
            binding.orderDelivered.visibility= View.GONE
        }
        if (!order.status.equals("Order Placed")||!isAdmin){
            binding.confirmOrder.visibility= View.GONE
        }

        binding.confirmOrder.setOnClickListener {
            order.status="Order Confirmed"
            //TODO: Update order in database
        }

        binding.orderDelivered.setOnClickListener {
            order.status="Delivered"
            //TODO: Update order in database
        }

        binding.submit.setOnClickListener {
            order.review=binding.review.text.toString()
            //TODO: Update order in database
        }

    }
}