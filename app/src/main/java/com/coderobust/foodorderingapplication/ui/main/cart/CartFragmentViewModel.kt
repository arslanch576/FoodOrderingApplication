package com.coderobust.foodorderingapplication.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderobust.foodorderingapplication.model.repositories.AuthRepository
import com.coderobust.foodorderingapplication.model.repositories.CartItemRepository
import com.coderobust.foodorderingapplication.ui.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(val cartItemRepository: CartItemRepository, val authRepository: AuthRepository):ViewModel() {
    val cartItems= MutableStateFlow<List<CartItem>>(emptyList())

    init {
        loadCartItems()
    }
    fun loadCartItems(){
        viewModelScope.launch {
            cartItemRepository.loadCartItems(authRepository.getCurrentUser()?.uid!!).collect{
                cartItems.value=it
            }
        }
    }
}