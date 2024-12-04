package com.coderobust.handcraftsshop.ui.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.coderobust.foodorderingapplication.databinding.ItemCartBinding
import com.coderobust.foodorderingapplication.databinding.ItemFoodBinding
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.viewHolders.BaseViewHolder

class CartItemViewHolder(val binding: ItemCartBinding) : BaseViewHolder(binding.root) {
    override fun bind(data: Any) {
        val item=data as CartItem

        binding.textFoodName.text = item.foodItem?.name
        binding.textQuantity.text = "${item.quantity}"
        binding.textFoodPrice.text = "$${item.foodItem?.price?:0*item.quantity}"
    }

}