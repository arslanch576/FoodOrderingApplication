package com.coderobust.handcraftsshop.ui.viewHolders

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.coderobust.foodorderingapplication.databinding.ItemCartBinding
import com.coderobust.foodorderingapplication.databinding.ItemFoodBinding
import com.coderobust.foodorderingapplication.databinding.ItemOrderBinding
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.Order
import com.coderobust.foodorderingapplication.ui.OrderDetailsActivity
import com.coderobust.foodorderingapplication.ui.fooditems.FoodItemDetailsActivity
import com.coderobust.foodorderingapplication.ui.viewHolders.BaseViewHolder
import com.google.gson.Gson

class OrderViewHolder(val binding: ItemOrderBinding) : BaseViewHolder(binding.root) {
    override fun bind(data: Any) {
        val item = data as Order

        binding.textFoodName.text = item.customerName
        binding.textFoodPrice.text = "${item.total} Rs."
        binding.textFoodItems.text = item.orderItems.map { it.name }.joinToString(", ")
        binding.status.text = item.status
        binding.root.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context, OrderDetailsActivity::class.java).putExtra("data", Gson().toJson(item)))
        }
    }

}