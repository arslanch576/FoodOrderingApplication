package com.coderobust.foodorderingapplication.ui.main.cart

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
import com.coderobust.foodorderingapplication.databinding.FragmentCartBinding
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import com.coderobust.foodorderingapplication.ui.main.home.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: GenericAdapter
    val items = ArrayList<CartItem>()
    val viewModel: CartFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GenericAdapter(items)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.cartItems.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                    binding.tvTotalProducts.text = "Total Products: ${it.size}"
                }
            }
        }
        lifecycleScope.launch {
            viewModel.total.collect {
                binding.tvTotalCart.text = "Total: ${it} Rs."
            }
        }
        lifecycleScope.launch {
            viewModel.isOrderPlaced.collect {
                if (it != null) {
                    Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                if (it != null) {
                    Toast.makeText(context, "Error: ${it}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnProceed.setOnClickListener {
            binding.etPostalAddress.visibility = View.VISIBLE
            binding.btnPlaceOrder.visibility = View.VISIBLE
            binding.btnProceed.visibility = View.GONE
        }

        binding.btnPlaceOrder.setOnClickListener {
            if (binding.etPostalAddress.text.toString().isEmpty()) {
                Toast.makeText(context, "Enter Postal Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.placeOrder(binding.etPostalAddress.text.toString())
        }
    }

}