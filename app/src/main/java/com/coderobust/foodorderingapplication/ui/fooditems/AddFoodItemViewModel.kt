package com.coderobust.foodorderingapplication.ui.fooditems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddFoodItemViewModel(val repository: FoodItemRepository): ViewModel() {

    val isSaved=MutableStateFlow<Boolean?>(null)
    val failureMessage=MutableStateFlow<String?>(null)

    fun saveFoodItem(foodItem: FoodItem) {
        viewModelScope.launch {
            val result=repository.saveFoodItem(foodItem)
            if (result.isSuccess){
                isSaved.value=true
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }
}