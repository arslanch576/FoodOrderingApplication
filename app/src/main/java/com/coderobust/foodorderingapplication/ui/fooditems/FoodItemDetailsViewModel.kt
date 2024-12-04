package com.coderobust.foodorderingapplication.ui.fooditems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.coderobust.foodorderingapplication.model.repositories.CartItemRepository
import com.coderobust.foodorderingapplication.ui.CartItem
import com.coderobust.foodorderingapplication.ui.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodItemDetailsViewModel @Inject constructor(val authRepository:AuthRepository,val cartItemRepository: CartItemRepository):ViewModel() {

    val cartItem= MutableStateFlow<CartItem?>(null)
    val doesExistInCart= MutableStateFlow<Boolean?>(null)
    val failureMessage= MutableStateFlow<String?>(null)

    fun checkInCart(foodItem: FoodItem){
        viewModelScope.launch {
            cartItemRepository.loadCartItem(foodItem.id,authRepository.getCurrentUser()?.uid!!).collect{
                if (it.isNotEmpty())
                    cartItem.value=it.get(0)
            }

        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            item.userId=authRepository.getCurrentUser()?.uid!!
            val result=cartItemRepository.saveCartItem(item)
            if (result.isSuccess) {
                cartItem.value=item
                doesExistInCart.value=true
            }else{
                failureMessage.value=result.exceptionOrNull()?.message
            }
        }
    }


}