package com.coderobust.foodorderingapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coderobust.foodorderingapplication.R
import com.coderobust.foodorderingapplication.databinding.FragmentDealsBinding
import com.coderobust.foodorderingapplication.databinding.FragmentMainBinding

class DealsFragment : Fragment() {

    lateinit var binding:FragmentDealsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDealsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}