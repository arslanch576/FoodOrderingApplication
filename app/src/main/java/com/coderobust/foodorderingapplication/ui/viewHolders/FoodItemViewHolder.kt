package com.coderobust.handcraftsshop.ui.viewHolders

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.coderobust.foodorderingapplication.databinding.ActivityFoodItemDetailsBinding
import com.coderobust.foodorderingapplication.databinding.ItemFoodBinding
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.fooditems.FoodItemDetailsActivity
import com.coderobust.foodorderingapplication.ui.viewHolders.BaseViewHolder
import com.google.gson.Gson

class FoodItemViewHolder(val binding: ItemFoodBinding) : BaseViewHolder(binding.root) {
    override fun bind(data: Any) {
        val item=data as FoodItem

        binding.textName.text = item.name
        binding.textCategory.text = item.category
        binding.textDescription.text = item.description
        binding.textPrice.text = "$${item.price}"
        binding.textRating.text = "⭐ ${item.rating}"

        binding.root.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context, FoodItemDetailsActivity::class.java).putExtra("data", Gson().toJson(item)))
        }
    }

}