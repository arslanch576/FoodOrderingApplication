package com.coderobust.foodorderingapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(val foodItemRepository: FoodItemRepository): ViewModel() {

    val foodItems= MutableStateFlow<List<FoodItem>>(emptyList())

    init {
        loadFoodItems()
    }
    fun loadFoodItems(){
        viewModelScope.launch {
            foodItemRepository.loadFoodItems().collect{
                foodItems.value=it
            }
        }
    }
}