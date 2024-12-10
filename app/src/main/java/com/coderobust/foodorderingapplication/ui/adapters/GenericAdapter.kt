package com.coderobust.constructioncosttracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderobust.foodorderingapplication.databinding.ItemCartBinding
import com.coderobust.foodorderingapplication.databinding.ItemFoodBinding
import com.coderobust.foodorderingapplication.databinding.ItemOrderBinding
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.Order
import com.coderobust.foodorderingapplication.ui.viewHolders.BaseViewHolder
import com.coderobust.handcraftsshop.ui.viewHolders.CartItemViewHolder
import com.coderobust.handcraftsshop.ui.viewHolders.FoodItemViewHolder
import com.coderobust.handcraftsshop.ui.viewHolders.OrderViewHolder

class GenericAdapter(val items: List<Any>) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 0) {
            val binding =
                ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FoodItemViewHolder(binding)
        }else if (viewType==1){
            val binding =
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CartItemViewHolder(binding)
        }else{
            val binding =
                ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OrderViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.get(position) is FoodItem) return 0
        if (items.get(position) is CartItem) return 1
        if (items.get(position) is Order) return 2
        return 3
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items.get(position))
    }
}