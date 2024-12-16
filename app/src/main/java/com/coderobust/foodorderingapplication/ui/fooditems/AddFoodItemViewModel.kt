package com.coderobust.foodorderingapplication.ui.fooditems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.FoodItemRepository
import com.coderobust.foodorderingapplication.model.repositories.StorageRepository
import com.coderobust.foodorderingapplication.ui.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodItemViewModel @Inject constructor(val repository: FoodItemRepository,val storageRepository: StorageRepository): ViewModel() {

    val isSaved=MutableStateFlow<Boolean?>(null)
    val failureMessage=MutableStateFlow<String?>(null)

    fun uploadImageAndSaveFoodItem(filePath:String,foodItem: FoodItem){
        storageRepository.uploadFile(filePath,onComplete = { success, result ->
            if (success){
                foodItem.image=result!!
                saveFoodItem(foodItem)
            }else{
                failureMessage.value=result
            }
        })
    }

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