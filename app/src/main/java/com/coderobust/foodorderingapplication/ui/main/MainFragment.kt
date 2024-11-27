package com.coderobust.foodorderingapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.FragmentMainBinding
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel= MainViewModel(FoodItemRepository())

        lifecycleScope.launch {
            viewModel.foodItems.collect{
                Toast.makeText(context, "Food items loaded (${it.size})", Toast.LENGTH_SHORT).show()
            }
        }
    }

}