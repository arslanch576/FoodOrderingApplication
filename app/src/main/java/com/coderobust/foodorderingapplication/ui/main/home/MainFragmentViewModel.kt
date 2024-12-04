package com.coderobust.foodorderingapplication.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(val foodItemRepository: FoodItemRepository):ViewModel() {
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