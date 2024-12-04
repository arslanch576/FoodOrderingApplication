package com.coderobust.foodorderingapplication.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Any)
}