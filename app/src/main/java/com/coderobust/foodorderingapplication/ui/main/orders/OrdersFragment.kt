package com.coderobust.foodorderingapplication.ui.main.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coderobust.constructioncosttracker.adapters.GenericAdapter
import com.coderobust.foodorderingapplication.databinding.FragmentMainBinding
import com.coderobust.foodorderingapplication.databinding.FragmentOrdersBinding
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.Order
import com.coderobust.foodorderingapplication.ui.main.home.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    lateinit var binding:FragmentOrdersBinding
    lateinit var adapter: GenericAdapter
    val items=ArrayList<Order>()
    val viewModel: OrderFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOrdersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= GenericAdapter(items)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.orders.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}